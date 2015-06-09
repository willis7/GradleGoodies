package com.acme.odp.email

import com.acme.odp.ODPConfig
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail
//import org.apache.log4j.Logger

/**
 * User: ksipe
 * New Date: 2/4/11 11:16 AM
 */
@Singleton
class Email {

    def config = ODPConfig.instance.email
//    static Logger logger = Logger.getInstance(Email.class)


    def void sendErrorMsg(String message) {

        send("ODP Error with ${System.props.env} Env", message)

    }

    def void send(String subject, String message) {

//        logger.debug "sending email subject:${subject} message:${message}"

        if (config.enabled) {
            try {
                def email = new SimpleEmail(hostName: config.host, smtpPort: config.port)
                if (config.uid && config.pw) {
                    email.setAuthenticator(new DefaultAuthenticator(config.uid, config.pw))
                }
                email.setTLS(true)
                email.setFrom(config.from)
                email.setSubject(subject)
                email.setMsg(message)

                config.to.each { address ->
                    email.addTo(address)
                }
                email.send()
            } catch (Exception e) {
//                logger.error("Issue sending email", e)
                config.enabled = false // turn off email if it isn't working
            }
        }
    }

    def void cmdLineSend(String subject, String message) {

        // temp store current setting... and override it
        boolean temp = config.enabled
        config.enabled = true
        send(subject,message)
        config.enabled = temp
    }

    static def void validateEmailConfig() {
        def config = ODPConfig.instance.email

        try {
            if (!config.host || !config.port || config.to?.size < 1) {
                turnOffEmail(config)
            }
        } catch (Exception e) {
            turnOffEmail(config)
        }
    }

    private static def turnOffEmail(config) {
//        logger.warn "email is NOT configured and will be disabled"
        config.enabled = false
    }
}

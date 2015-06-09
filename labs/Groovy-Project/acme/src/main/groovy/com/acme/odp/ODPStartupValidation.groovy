package com.acme.odp
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.PropertyConfigurator

/**
 * User: ksipe
 * New Date: 1/27/11 9:29 PM
 */
@Singleton
class ODPStartupValidation {

    def void init(String cmdLineEnv) {

        def env = System.props.env ?: "dev"
        if (cmdLineEnv) {
            env = cmdLineEnv
        }

        System.props.env = env  // this ensures that the sys prop is consistent with running configuration

        println "****************************************"
        println "Startup env is: ${env}"

        def config = new ConfigSlurper(env).parse(new File('odp.config').toURL())

        ODPConfig sysConfig = ODPConfig.instance

        sysConfig.email = config.email
        println "Email is configured for smtp: ${sysConfig.email.host}"
        println "****************************************"

//        BasicConfigurator.configure()
//        PropertyConfigurator.configure(config.toProperties())
    }
}
package com.acme.odp


import com.acme.odp.email.Email
import java.util.jar.Attributes
import java.util.jar.JarFile
import java.util.jar.Manifest

/**
 * User: ksipe
 * New Date: 2/3/11 10:29 AM
 */
class ODP {

    static void main(String[] args) {

        def cli = new CliBuilder(usage: 'java com.acme.odp.ODP -[hveE]')
        cli.h(longOpt: 'help', 'usage information', required: false)
        cli.v(longOpt: 'version', 'version information', required: false)
        cli.e(longOpt: 'email', 'send email test message <arg> is the message', required: false, args: 1, argName: 'message', optionalArg: true)
        cli.E(longOpt: 'env', 'overrides the env to the passed in value', required: false, args: 1)
        OptionAccessor opt = cli.parse(args)

        if (!opt) {
            return
        }

        if (opt.h) {
            cli.usage()
            return
        }
        if (opt.v) {

            def odpVersion = getOdpVersion()
            println "------------------------------------------------------------"
            if (odpVersion) {
                println "ODP Version: ${odpVersion}"
            } else {
                println "ODP version information only available when running from a Jar"
            }
            println "Java Version: ${System.props.'java.version'}"
            println "OS Version: ${System.props.'os.version'}"
            println "------------------------------------------------------------"
            return
        }

        println "Starting ODP (initializing)..."
        String env = null
        if (opt.E) {
            env = opt.E
        }

        ODPStartupValidation.instance.init(env) // logging configured after this point

        Email.validateEmailConfig()

        if (opt.e) {
            def message = "Test message from ODP"
            if (!opt.e.equals(true)) {
                message = opt.e
            }

            Email.instance.cmdLineSend("Msg from ODP - ${System.props.env}", message)

            if (ODPConfig.instance.email.enabled) {
                println "Email(s) sent... Emails are currently set to be enabled"
            } else {
                println "Email(s) sent.. Emails are currently set to be disabled"
            }

        }
    }

    private static def getOdpVersion() {
        def version = ''

        def jarFile = new File("acme-odp.jar")
        if (jarFile.exists()) {
            JarFile jar = new JarFile(jarFile)
            Manifest manifest = jar.getManifest();
            Attributes attrs = manifest.getMainAttributes();
            version = attrs.getValue('Implementation-Version')
        }
        return version
    }
}

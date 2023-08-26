import tw.com.pollochang.security.UserPasswordEncoderListener
import tw.com.pollochang.security.CustomUserDetailsService

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    userDetailsService(CustomUserDetailsService) // tag會用到
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("zh","TW")
        java.util.Locale.setDefault(defaultLocale)
    }
}

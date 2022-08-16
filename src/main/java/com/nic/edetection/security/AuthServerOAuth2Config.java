//package com.nic.edetection.security;
//package com.nic.edetection.auth_filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthServerOAuth2Config
//  extends AuthorizationServerConfigurerAdapter {
// 
//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public void configure(
//      AuthorizationServerSecurityConfigurer oauthServer) 
//      throws Exception {
//        oauthServer
//          .tokenKeyAccess("permitAll()")
//          .checkTokenAccess("isAuthenticated()");
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) 
//      throws Exception {
//        clients.jdbc(dataSource())
//          .withClient("sampleClientId")
//          .authorizedGrantTypes("implicit")
//          .scopes("read")
//          .autoApprove(true)
//          .and()
//          .withClient("clientIdPassword")
//          .secret("secret")
//          .authorizedGrantTypes(
//            "password","authorization_code", "refresh_token")
//          .scopes("read");
//    }
//
//    @Override 
//    public void configure(
//      AuthorizationServerEndpointsConfigurer endpoints) 
//      throws Exception {
// 
//        endpoints
//          .tokenStore(tokenStore())
//          .authenticationManager(authenticationManager);
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource());
//    }
//}
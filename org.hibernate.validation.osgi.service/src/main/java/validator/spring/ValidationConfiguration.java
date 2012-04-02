package validator.spring;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.ProviderSpecificBootstrap;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

/**
 * Configure our validators.
 * 
 */
@Configuration
public class ValidationConfiguration {
	
  private ApplicationContext applicationContext;

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Inject
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /**
   * Build and expose an instance of {@link OsgiValidatorFactoryBean}. This
   * class exposes the {@link javax.validation.Validator},
   * {@link javax.validation.ValidatorFactory} and
   * {@link org.springframework.validation.Validator} interfaces.
   * @return OsgiValidatorFactoryBean An instance of OsgiValidatorFactoryBean
   */
  @Bean
  public OsgiValidatorFactoryBean osgiValidatorFactoryBean() {
    // configure and build an instance of ValidatorFactory
    ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
        .byProvider(HibernateValidator.class);

    // bootstrap to properly resolve in an OSGi environment
    validationBootStrap.providerResolver(new HibernateValidationProviderResolver());

    HibernateValidatorConfiguration configure = validationBootStrap.configure();

    // configure Spring to autowire our constraints
    configure.constraintValidatorFactory(new SpringConstraintValidatorFactory(this.applicationContext
        .getAutowireCapableBeanFactory()));

    // now that we've done configuring the ValidatorFactory, let's build it
    ValidatorFactory validatorFactory = configure.buildValidatorFactory();

    // now build and return an OsgiValidatorFactoryBean
    return new OsgiValidatorFactoryBean(validatorFactory);
  }
}

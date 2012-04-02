package validator.spring;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * Subclass of
 * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter
 * SpringValidatorAdapter} that will expose a
 * {@link javax.validation.ValidatorFactory ValidatorFactory} through the
 * {@link org.springframework.validation.Validator} interface.
 * 
 */
public class OsgiValidatorFactoryBean extends SpringValidatorAdapter implements ValidatorFactory {

  /**
   * Constructor that takes an instance of {@link ValidatorFactory} to expose
   * through the {@link org.springframework.validation.Validator} interface.
   * @param validatorFactory ValidatorFactory instance
   */
  public OsgiValidatorFactoryBean(ValidatorFactory validatorFactory) {
    super(validatorFactory.getValidator());
    this.validatorFactory = validatorFactory;
  }

  private ValidatorFactory validatorFactory;

  public Validator getValidator() {
    return this.validatorFactory.getValidator();
  }

  public ValidatorContext usingContext() {
    return this.validatorFactory.usingContext();
  }

  public MessageInterpolator getMessageInterpolator() {
    return this.validatorFactory.getMessageInterpolator();
  }

  public TraversableResolver getTraversableResolver() {
    return this.validatorFactory.getTraversableResolver();
  }

  public ConstraintValidatorFactory getConstraintValidatorFactory() {
    return this.validatorFactory.getConstraintValidatorFactory();
  }
}

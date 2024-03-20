package fr.insee.queen.batch.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class to determine the MONGO Condition.
 * If we need to instantiante a MONGO connection or not
 * @author samco
 *
 */
public class ConditonMongo implements Condition{

	/**
	 * This method override the Condition.class and checking if
	 * the application needs to be launch in MONGO persistence mode
	 *
	 * There is no mongo anymore but we keep this class now as it is used by other jars
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return false;
	}
}

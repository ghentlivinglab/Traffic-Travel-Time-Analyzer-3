package settings.DependencyModules;


import be.ugent.tiwi.dal.*;
import com.google.inject.AbstractModule;

/** Klasse die de instellingen van de Guice dependency injection configureert.
 *
 */
public class RepositoryModule  extends AbstractModule {
        @Override
        protected void configure() {
            bind(IMetingRepository.class).to(MetingRepository.class);
            bind(IProviderRepository.class).to(ProviderRepository.class);
            bind(ITrajectRepository.class).to(TrajectRepository.class);
        }
}

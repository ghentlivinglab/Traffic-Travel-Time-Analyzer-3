package be.ugent.tiwi.settings.DependencyModules;


import be.ugent.tiwi.Mocks.MetingRepositoryMock;
import be.ugent.tiwi.Mocks.ProviderRepositoryMock;
import be.ugent.tiwi.Mocks.TrajectRepositoryMock;
import be.ugent.tiwi.dal.*;
import com.google.inject.AbstractModule;

/** Klasse die de instellingen van de Guice dependency injection configureert.
 *
 */
public class RepositoryModule  extends AbstractModule {
        @Override
        protected void configure() {
            bind(IMetingRepository.class).to(MetingRepositoryMock.class);
            bind(IProviderRepository.class).to(ProviderRepositoryMock.class);
            bind(ITrajectRepository.class).to(TrajectRepositoryMock.class);
        }
}

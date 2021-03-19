package ro.ubb.truckers.service;

import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.util.ServiceProvider;

public abstract class BaseService {
    protected final RepositoryProvider provider = ServiceProvider.inject(RepositoryProvider.class);
}

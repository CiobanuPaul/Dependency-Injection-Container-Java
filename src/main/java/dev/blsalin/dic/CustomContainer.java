package dev.blsalin.dic;

import java.util.function.Function;
import java.util.Map;

public interface CustomContainer extends AutoCloseable {
    <T> boolean addInstance(T instance) throws NullParameterNotAllowedException, InstancesCannotBeRedeclaredException;
    <T> boolean addInstance(T instance, String customName) throws NullParameterNotAllowedException, InstancesCannotBeRedeclaredException;

    <T> T getInstance(Class<T> type) throws NullParameterNotAllowedException, CannotProvideInstanceException;

    <T> T getInstance(Class<T> type, String customName) throws NullParameterNotAllowedException, CannotProvideInstanceException;

    <T> boolean addFactoryMethod(Class<T> type, Function<CustomContainer, T> factoryMethod) throws NullParameterNotAllowedException;

    <T> T create(Class<T> type);
    <T> T create(Class<T> type, Map<String, Object> parameters);
}

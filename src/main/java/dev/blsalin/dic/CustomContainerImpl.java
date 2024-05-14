package dev.blsalin.dic;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomContainerImpl implements CustomContainer {
    private Map<String, Object> instancesMap = new HashMap<>();
    private Map<String, Function<CustomContainer, ?>> functions = new HashMap<>();

    public CustomContainerImpl() {
    }

    public CustomContainerImpl(Map<String, Object> instancesMap, Map<String, Function<CustomContainer, ?>> functions) {
        this.instancesMap.putAll(instancesMap);
        this.functions.putAll(functions);
    }

    @Override
    public <T> boolean addInstance(T instance) throws NullParameterNotAllowedException, InstancesCannotBeRedeclaredException {
        if(instance == null)
            throw new NullParameterNotAllowedException();
        return addInstance(instance, instance.getClass().getName());
    }

    @Override
    public <T> boolean addInstance(T instance, String customName) throws NullParameterNotAllowedException, InstancesCannotBeRedeclaredException {
        if(instance == null)
            throw new NullParameterNotAllowedException();
        if(customName == null)
            throw new NullParameterNotAllowedException();
        if(instancesMap.containsKey(customName)) {
            throw new InstancesCannotBeRedeclaredException();
        }
//        if(functions.containsKey(instance.getClass().getName())) {
//            var func = functions.get(instance.getClass().getName());
//            instance = (T) func.apply(this);
//        }
        instancesMap.put(customName, instance);
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) throws NullParameterNotAllowedException, CannotProvideInstanceException {
        if(type == null)
            throw new NullParameterNotAllowedException();
        return getInstance(type, type.getName());
    }

    @Override
    public <T> T getInstance(Class<T> type, String customName) throws NullParameterNotAllowedException, CannotProvideInstanceException {
        if(type == null){
            throw new NullParameterNotAllowedException();
        }
        if(customName == null)
            throw new NullParameterNotAllowedException();
//        if(parameters!=null && parameters.containsKey(customName)) {
//            return (T) parameters.get(customName);
//        }
        if(instancesMap.containsKey(customName)) {
            return (T) instancesMap.get(customName);
        }
        if(functions.containsKey(type.getName())) {
            var func = functions.get(type.getName());
            Object obj = func.apply(this);
            addInstance(obj);
            return (T) obj;
        }
        throw new CannotProvideInstanceException();
    }

//    private <T> T getInstance(Class<T> type, String customName, Map<String, Object> parameters) throws NullParameterNotAllowedException, CannotProvideInstanceException {
//
//    }

    @Override
    public <T> boolean addFactoryMethod(Class<T> type, Function<CustomContainer, T> factoryMethod) throws NullParameterNotAllowedException {
        if(type == null || factoryMethod == null){
            throw new NullParameterNotAllowedException();
        }
        if(functions.containsKey(type.getName())) {
            return false;
        }
        functions.put(type.getName(), factoryMethod);
        return true;

    }

    @Override
    public <T> T create(Class<T> type) {
        return create(type, new HashMap<>());
    }

    @Override
    public <T> T create(Class<T> type, Map<String, Object> parameters) {
        Map<String, Object> newInstancesMap = new HashMap<>(parameters);
        for (var entry : instancesMap.entrySet()) {
            if(!newInstancesMap.containsKey(entry.getKey())) {
                newInstancesMap.put(entry.getKey(), entry.getValue());
            }
        }
        newInstancesMap.remove(type.getName());
        try(var newCCI = new CustomContainerImpl(newInstancesMap, functions)) {
            return newCCI.getInstance(type, type.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        for (var value : instancesMap.values()) {
            if (value instanceof Closeable) {
                ((Closeable) value).close();
            }
        }
    }
}

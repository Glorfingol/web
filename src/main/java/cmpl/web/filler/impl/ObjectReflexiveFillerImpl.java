package cmpl.web.filler.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpl.web.filler.ObjectReflexiveFiller;
import cmpl.web.model.news.METHOD;

public class ObjectReflexiveFillerImpl implements ObjectReflexiveFiller {

  private Object origin;
  private Object destination;

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectReflexiveFillerImpl.class);

  private ObjectReflexiveFillerImpl(Object origin, Object destination) {
    this.origin = origin;
    this.destination = destination;
  }

  public static ObjectReflexiveFillerImpl fromOriginAndDestination(Object origin, Object destination) {
    return new ObjectReflexiveFillerImpl(origin, destination);
  }

  @Override
  public void fillDestination() {
    invokeDestinationFieldsSetters();
  }

  private void invokeDestinationFieldsSetters() {
    List<Field> destinationFields = getFields(destination.getClass());
    for (Field destinationField : destinationFields) {
      List<Field> originFields = getFields(origin.getClass());
      try {
        Method setterDestination = computeSetterDestination(destination, destinationField);
        invokeDestinationSetterIfPossible(origin, destination, originFields, destinationField, setterDestination);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        LOGGER.error("Impossible de remplir l'objet " + destination + " avec l'origine " + origin, e);
      }
    }
  }

  private List<Field> getFields(Class<?> classObject) {

    List<Field> fields = new ArrayList<>();

    List<Field> classFields = Arrays.asList(classObject.getDeclaredFields());
    fields.addAll(classFields);

    if (classObject.getSuperclass() != null) {
      fields.addAll(getFields(classObject.getSuperclass()));
    }

    return fields;

  }

  private Method computeSetterDestination(Object destination, Field destinationField) throws NoSuchMethodException {
    return destination.getClass().getMethod(getSetterName(destinationField), destinationField.getType());
  }

  private void invokeDestinationSetterIfPossible(Object origin, Object destination, List<Field> originFields,
      Field destinationField, Method setterDestination) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException {

    Field originField = getCorrespondingOriginFieldForDestinationField(originFields, destinationField);
    if (canInvokeSetter(originField)) {
      Method getterOrigin = computeGetterOrigin(origin, originField);
      setterDestination.invoke(destination, getterOrigin.invoke(origin));
    }
  }

  private Field getCorrespondingOriginFieldForDestinationField(List<Field> originFields, Field destinationField) {
    for (Field originField : originFields) {
      if (originField.getName().equals(destinationField.getName())) {
        return originField;
      }
    }
    return null;
  }

  private boolean canInvokeSetter(Field field) {
    return field != null;
  }

  private Method computeGetterOrigin(Object origin, Field originField) throws NoSuchMethodException {
    return origin.getClass().getMethod(getGetterName(originField));
  }

  private String getSetterName(Field field) {
    return getMethodName(METHOD.SETTER, field);
  }

  private String getGetterName(Field field) {
    if (boolean.class.equals(field.getType())) {
      return getMethodName(METHOD.BOOLEAN_GETTER, field);
    } else {
      return getMethodName(METHOD.GETTER, field);
    }

  }

  private String getMethodName(METHOD method, Field field) {
    StringBuilder sb = new StringBuilder();
    sb.append(method.getPrefix());

    sb.append(field.getName().replaceFirst(field.getName().substring(0, 1),
        field.getName().substring(0, 1).toUpperCase()));

    return sb.toString();
  }

}

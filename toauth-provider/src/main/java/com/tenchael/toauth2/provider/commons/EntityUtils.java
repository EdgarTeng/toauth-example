package com.tenchael.toauth2.provider.commons;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityUtils {
	public static String toString(final Object obj, String[] attrs) {
		Map<String, Object> map = getGetMethodsValue(obj);
		Set<String> keys = map.keySet();
		StringBuffer buffer = new StringBuffer();
		if (attrs != null && attrs.length > 0) {
			buffer.append("{");
			for (String attr : attrs) {
				if (keys.contains(attr)) {
					Object value = map.get(attr);
					buffer.append(attr);
					buffer.append("=");
					buffer.append(value.toString());
					buffer.append(",");
				}
			}
			if (buffer.toString().endsWith(",")) {
				buffer.deleteCharAt(buffer.lastIndexOf(","));
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	public static Map<String, Object> getGetMethodsValue(Object obj) {
		Map<String, Object> keyValue = new HashMap<String, Object>();
		try {
			Class clazz = obj.getClass();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
						clazz);
				Method getMethod = pd.getReadMethod();
				Object value = getMethod.invoke(obj);
				keyValue.put(field.getName(), value);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return keyValue;
	}

}

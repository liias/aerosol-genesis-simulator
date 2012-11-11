package ee.ut.physic.aerosol.simulator.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class JsonExclusionStrategy implements ExclusionStrategy {
    private Class<?> typeToSkip;

    public JsonExclusionStrategy() {
    }

    public JsonExclusionStrategy(Class<?> typeToSkip) {
        this.typeToSkip = typeToSkip;
    }

    public boolean shouldSkipClass(Class<?> clazz) {
        return (clazz == typeToSkip);
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        List<Class> excludedAnnotations = new ArrayList<Class>(2);
        excludedAnnotations.add(Transient.class);
        excludedAnnotations.add(ExcludeFromJson.class);
        for (Class<Annotation> excludedAnnotation : excludedAnnotations) {
            if (fieldAttributes.getAnnotation(excludedAnnotation) != null) {
                return true;
            }
        }
        return false;
    }
}
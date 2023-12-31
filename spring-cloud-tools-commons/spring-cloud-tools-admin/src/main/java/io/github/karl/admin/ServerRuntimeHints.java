package io.github.karl.admin;

import com.fasterxml.jackson.databind.ser.std.ClassSerializer;
import com.fasterxml.jackson.databind.ser.std.FileSerializer;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.*;
import de.codecentric.boot.admin.server.domain.values.*;
import de.codecentric.boot.admin.server.ui.config.AdminServerUiProperties;
import de.codecentric.boot.admin.server.ui.config.CssColorUtils;
import de.codecentric.boot.admin.server.ui.web.UiController;
import de.codecentric.boot.admin.server.utils.jackson.*;
import de.codecentric.boot.admin.server.web.InstanceWebProxy;
import lombok.SneakyThrows;
import org.springframework.aot.hint.*;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        registerReflectionHints(hints);

        registerResourcesHints(hints);

        registerSerializationHints(hints);
    }

    private static void registerSerializationHints(RuntimeHints hints) {
        hints.serialization()
                .registerType(HashMap.class)
                .registerType(ArrayList.class)
                .registerType(Registration.class)
                .registerType(InstanceId.class)
                .registerType(Instance.class)
                .registerType(BuildVersion.class)
                .registerType(Endpoint.class)
                .registerType(Endpoints.class)
                .registerType(Info.class)
                .registerType(StatusInfo.class)
                .registerType(Tags.class);
    }

    private static void registerResourcesHints(RuntimeHints hints) {
        hints.resources()
                .registerPattern("**/spring-boot-admin-server-ui/**.*")
                .registerPattern("**/sba-settings.js")
                .registerPattern("**/variables.css");
    }

    @SneakyThrows
    private static void registerReflectionHints(RuntimeHints hints) {
        Class<?> queryEndpointStrategyResponse = Class
                .forName("de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response");
        Class<?> queryEndpointStrategyResponseEndpointRef = Class.forName(
                "de.codecentric.boot.admin.server.services.endpoints.QueryIndexEndpointStrategy$Response$EndpointRef");

        hints.reflection()
                .registerType(queryEndpointStrategyResponse, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(queryEndpointStrategyResponseEndpointRef, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(UiController.Settings.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(AdminServerUiProperties.UiTheme.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(AdminServerUiProperties.Palette.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(CssColorUtils.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceDeregisteredEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceEndpointsDetectedEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceInfoChangedEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceRegisteredEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceRegistrationUpdatedEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceStatusChangedEvent.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceId.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(Endpoint.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(Instance.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceId.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceWebProxy.InstanceResponse.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceWebProxy.ForwardRequest.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)

                .registerType(BuildVersionMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(EndpointMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(EndpointsMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InfoMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceDeregisteredEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceEndpointsDetectedEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceIdMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceInfoChangedEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceRegisteredEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceRegistrationUpdatedEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(InstanceStatusChangedEventMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(StatusInfoMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
                .registerType(TagsMixin.class, MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)

                .registerConstructor(queryEndpointStrategyResponse.getConstructors()[0], ExecutableMode.INVOKE)
                .registerConstructor(
                        queryEndpointStrategyResponseEndpointRef.getDeclaredConstructor(String.class, boolean.class),
                        ExecutableMode.INVOKE)

                .registerConstructor(Registration.class.getDeclaredConstructor(String.class, String.class, String.class,
                        String.class, String.class, Map.class), ExecutableMode.INVOKE)
                .registerConstructor(Registration.Builder.class.getDeclaredConstructor(), ExecutableMode.INVOKE)
                .registerMethod(Registration.Builder.class.getMethod("build"), ExecutableMode.INVOKE)
                .registerMethod(Registration.class.getMethod("toBuilder"), ExecutableMode.INVOKE)
                .registerTypes(TypeReference.listOf(StdJdkSerializers.AtomicBooleanSerializer.class,
                                StdJdkSerializers.AtomicIntegerSerializer.class, StdJdkSerializers.AtomicLongSerializer.class,
                                FileSerializer.class, ClassSerializer.class, TokenBufferSerializer.class),
                        TypeHint.builtWith(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS));
    }

}
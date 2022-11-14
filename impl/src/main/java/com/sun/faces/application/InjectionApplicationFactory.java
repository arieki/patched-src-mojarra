/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.application;

import static com.sun.faces.util.Util.notNull;
import static java.text.MessageFormat.format;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

import com.sun.faces.util.FacesLogger;

/**
 * This {@link javax.faces.application.ApplicationFactory} is responsible for injecting the default
 * {@link Application} instance into the top-level {@link Application} as configured by the runtime.
 * 
 * <p>
 * Doing this allows us to preserve backwards compatibility as the API evolves without having the
 * API rely on implementation specific details.
 * </p>
 */
//Portions Copyright [2018] Payara Foundation and/or affiliates
public class InjectionApplicationFactory extends ApplicationFactory {

    private static final Logger LOGGER = FacesLogger.APPLICATION.getLogger();

    private Application defaultApplication;
    private Field defaultApplicationField;
    private final Map<String, Application> applicationHolder = new ConcurrentHashMap<>(1);

    // ------------------------------------------------------------ Constructors

    public InjectionApplicationFactory(ApplicationFactory delegate) {
        super(delegate);
        notNull("applicationFactory", delegate);
    }

    // ----------------------------------------- Methods from ApplicationFactory

    @Override
    public Application getApplication() {
    		return applicationHolder.computeIfAbsent("default", e -> {
    			Application application = getWrapped().getApplication();

                if (application == null) {
                    throw new IllegalStateException(
                        format("Delegate ApplicationContextFactory, {0}, returned null when calling getApplication().", getWrapped().getClass().getName()));
                }
                
                injectDefaultApplication(application);

             return application;
         });
    }

    @Override
    public synchronized void setApplication(Application application) {
    		applicationHolder.put("default", application);
        getWrapped().setApplication(application);
        injectDefaultApplication(application);
    }

    // --------------------------------------------------------- Private Methods

    private void injectDefaultApplication(Application application) {

        if (defaultApplication == null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            defaultApplication = InjectionApplicationFactory.removeApplicationInstance(ctx.getExternalContext().getApplicationMap());
        }

        if (defaultApplication != null) {
            try {
                if (defaultApplicationField == null) {
                    defaultApplicationField = Application.class.getDeclaredField("defaultApplication");
                    defaultApplicationField.setAccessible(true);
                }
                defaultApplicationField.set(application, defaultApplication);

            } catch (NoSuchFieldException nsfe) {
                if (LOGGER.isLoggable(FINE)) {
                    LOGGER.log(FINE, "Unable to find private field named 'defaultApplication' in javax.faces.application.Application.");
                }
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                if (LOGGER.isLoggable(SEVERE)) {
                    LOGGER.log(SEVERE, e.toString(), e);
                }
            }
        }
    }

    // ------------------------------------------------- Package private Methods

    static void setApplicationInstance(Application app) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(InjectionApplicationFactory.class.getName(), app);
    }

    static Application removeApplicationInstance(Map<String, Object> appMap) {
        return (Application) appMap.remove(InjectionApplicationFactory.class.getName());
    }

}

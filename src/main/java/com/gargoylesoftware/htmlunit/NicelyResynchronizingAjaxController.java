/*
 * Copyright (c) 2002-2008 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit;

import java.lang.ref.WeakReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This {@link AjaxController} resynchronizes calls calling from the main thread.
 * The idea is that asynchron AJAX calls performed directly in response to a user
 * action (therefore in the "main" thread and not in the thread of a background task)
 * are directly useful for the user. To easily have a testable state, these calls
 * are performed synchron.
 *
 * @version $Revision: 3075 $
 * @author Marc Guillemot
 */
public class NicelyResynchronizingAjaxController extends AjaxController {

    private static final long serialVersionUID = -5406000795046341395L;

    private final WeakReference<Thread> originatedThread_;

    /**
     * Creates an instance.
     */
    public NicelyResynchronizingAjaxController() {
        originatedThread_ = new WeakReference<Thread>(Thread.currentThread());
    }

    /**
     * Returns the log.
     * @return the log
     */
    protected final Log getLog() {
        return LogFactory.getLog(getClass());
    }

    /**
     * Resynchronizes calls performed from the thread where this instance has
     * been created.
     * {@inheritDoc}
     */
    @Override
    public boolean processSynchron(final HtmlPage page, final WebRequestSettings requestSettings,
            final boolean async) {

        if (async && isInOriginalThread()) {
            getLog().info("Re-synchronized call to " + requestSettings.getUrl());
            return true;
        }
        return !async;
    }

    /**
     * Indicates if currently executing thread is the one in which this instance has been
     * created
     * @return <code>true</code> if it's the same thread
     */
    boolean isInOriginalThread() {
        return Thread.currentThread() == originatedThread_.get();
    }
}

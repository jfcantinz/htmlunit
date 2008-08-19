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
package com.gargoylesoftware.htmlunit.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;

/**
 * Provides a convenient implementation of the {@link WebResponse} interface that can be subclassed
 * by developers wishing to adapt a particular WebResponse.
 * This class implements the Wrapper or Decorator pattern. Methods default to calling through to the wrapped
 * web connection object.
 *
 * @version $Revision: 3026 $
 * @author Marc Guillemot
 * @author Ahmed Ashour
 */
public class WebResponseWrapper implements WebResponse {
    private final WebResponse wrappedWebResponse_;

    /**
     * Constructs a WebResponse object wrapping provided WebResponse.
     * @param webResponse the webResponse that does the real work
     * @throws IllegalArgumentException if the connection is <code>null</code>
     */
    public WebResponseWrapper(final WebResponse webResponse) throws IllegalArgumentException {
        if (webResponse == null) {
            throw new IllegalArgumentException("Wrapped WebResponse can't be null");
        }
        wrappedWebResponse_ = webResponse;
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getContentAsStream() on the wrapped connection object.
     */
    public InputStream getContentAsStream() throws IOException {
        return wrappedWebResponse_.getContentAsStream();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getContentAsString() on the wrapped connection object.
     */
    public String getContentAsString() {
        return wrappedWebResponse_.getContentAsString();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getContentCharSet() on the wrapped connection object.
     */
    public String getContentCharSet() {
        return wrappedWebResponse_.getContentCharSet();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getContentType() on the wrapped connection object.
     */
    public String getContentType() {
        return wrappedWebResponse_.getContentType();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getLoadTimeInMilliSeconds() on the wrapped connection object.
     */
    public long getLoadTimeInMilliSeconds() {
        return wrappedWebResponse_.getLoadTimeInMilliSeconds();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getRequestMethod() on the wrapped connection object.
     */
    public HttpMethod getRequestMethod() {
        return wrappedWebResponse_.getRequestMethod();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getResponseBody() on the wrapped connection object.
     */
    public byte[] getResponseBody() {
        return wrappedWebResponse_.getResponseBody();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getResponseHeaders() on the wrapped connection object.
     */
    public List<NameValuePair> getResponseHeaders() {
        return wrappedWebResponse_.getResponseHeaders();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getResponseHeaderValue() on the wrapped connection object.
     */
    public String getResponseHeaderValue(final String headerName) {
        return wrappedWebResponse_.getResponseHeaderValue(headerName);
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getStatusCode() on the wrapped connection object.
     */
    public int getStatusCode() {
        return wrappedWebResponse_.getStatusCode();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getStatusMessage() on the wrapped connection object.
     */
    public String getStatusMessage() {
        return wrappedWebResponse_.getStatusMessage();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getUrl() on the wrapped connection object.
     */
    public URL getUrl() {
        return wrappedWebResponse_.getUrl();
    }

    /**
     * {@inheritDoc}
     * The default behavior of this method is to return getRequestSettings() on the wrapped connection object.
     */
    public WebRequestSettings getRequestSettings() {
        return wrappedWebResponse_.getRequestSettings();
    }
}

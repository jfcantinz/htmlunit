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
package com.gargoylesoftware.htmlunit.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringEscapeUtils;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.SgmlPage;

/**
 * Wrapper for the HTML element "input".
 *
 * @version $Revision: 3075 $
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author David K. Taylor
 * @author <a href="mailto:cse@dynabean.de">Christian Sell</a>
 * @author Daniel Gredler
 * @author Ahmed Ashour
 */
public class HtmlSubmitInput extends HtmlInput {

    private static final long serialVersionUID = -615974535731910492L;

    /**
     * Value to use if no specified <tt>value</tt> attribute.
     */
    private static final String DEFAULT_VALUE = "Submit Query";

    /**
     * Creates an instance.
     *
     * @param namespaceURI the URI that identifies an XML namespace
     * @param qualifiedName the qualified name of the element type to instantiate
     * @param page the page that contains this element
     * @param attributes the initial attributes
     */
    HtmlSubmitInput(final String namespaceURI, final String qualifiedName, final SgmlPage page,
            final Map<String, DomAttr> attributes) {
        super(namespaceURI, qualifiedName, page, attributes);
        if (getPage().getWebClient().getBrowserVersion().isIE() && !isAttributeDefined("value")) {
            setAttributeValue("value", DEFAULT_VALUE);
        }
    }

    /**
     * This method will be called if there either wasn't an onclick handler or there was
     * but the result of that handler was true. This is the default behavior of clicking
     * the element. The default implementation returns the current page - subclasses
     * requiring different behavior (like {@link HtmlSubmitInput}) will override this
     * method.
     *
     * @param defaultPage the default page to return if the action does not
     * load a new page.
     * @return the page that is currently loaded after execution of this method
     * @throws IOException if an IO error occurred
     */
    @Override
    protected Page doClickAction(final Page defaultPage) throws IOException {
        final HtmlForm form = getEnclosingForm();
        if (form != null) {
            return form.submit(this);
        }
        return super.doClickAction(defaultPage);
    }

    /**
     * {@inheritDoc} This method <b>does nothing</b> for submit input elements.
     * @see SubmittableElement#reset()
     */
    @Override
    public void reset() {
        // Empty.
    }

    /**
     * {@inheritDoc} Returns "Submit Query" if <tt>value</tt> attribute is not defined.
     */
    @Override
    public String asText() {
        String text = super.asText();
        if (text == ATTRIBUTE_NOT_DEFINED) {
            text = DEFAULT_VALUE;
        }
        return text;
    }

    /**
     * {@inheritDoc} Doesn't print the attribute if it is <tt>value="Submit Query"</tt>.
     */
    @Override
    protected void printOpeningTagContentAsXml(final PrintWriter printWriter) {
        printWriter.print(getTagName());

        for (final DomAttr attribute : getAttributesCollection()) {
            if (!attribute.getNodeName().equals("value") || !attribute.getValue().equals(DEFAULT_VALUE)) {
                printWriter.print(" ");
                final String name = attribute.getNodeName();
                printWriter.print(name);
                printWriter.print("=\"");
                printWriter.print(StringEscapeUtils.escapeXml(attribute.getNodeValue()));
                printWriter.print("\"");
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * Returns "Submit Query" if <tt>name</tt> attribute is defined and <tt>value</tt> attribute is not defined.
     */
    @Override
    public NameValuePair[] getSubmitKeyValuePairs() {
        if (getNameAttribute().length() != 0 && !isAttributeDefined("value")) {
            return new NameValuePair[]{new NameValuePair(getNameAttribute(), DEFAULT_VALUE)};
        }
        return super.getSubmitKeyValuePairs();
    }
}

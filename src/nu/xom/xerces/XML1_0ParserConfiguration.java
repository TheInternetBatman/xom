// Copyright 2003, 2004 Elliotte Rusty Harold
// 
// This library is free software; you can redistribute 
// it and/or modify it under the terms of version 2.1 of 
// the GNU Lesser General Public License as published by  
// the Free Software Foundation.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General 
// Public License along with this library; if not, write to the 
// Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
// Boston, MA  02111-1307  USA
// 
// You can contact Elliotte Rusty Harold by sending e-mail to
// elharo@metalab.unc.edu. Please include the word "XOM" in the
// subject line. The XOM home page is temporarily located at
// http://www.cafeconleche.org/XOM/  but will eventually move
// to http://www.xom.nu/

package nu.xom.xerces;

import java.io.IOException;

import org.apache.xerces.impl.XMLEntityHandler;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.XNIException;

/**
 * <p>
 * This class is used by the <code>Builder</code> to prevent Xerces
 * from accepting XML 1.1 documents. You don't need to touch it
 * or use it. 
 * </p>
 * 
 * @author Elliotte Rusty Harold
 * @version 1.0a2
 * 
 */
class XML1_0ParserConfiguration extends XML11Configuration { 

    /** <p>
     *  Ensure that the parser is always configured to use 
     *  XML 1.0 exclusively. 
     *  </p>
     * 
     * @param complete true if the parser should finish parsing the
     *     rest of the document
     * 
     * @return true if more of the document remains to be parsed, 
     *     false otherwise
     * 
     * @see org.apache.xerces.xni.parser.XMLPullParserConfiguration#parse(boolean)
     */
    public boolean parse(boolean complete) 
      throws XNIException, IOException {

        if (fInputSource != null) {
            fValidationManager.reset();
            fVersionDetector.reset(this);
            resetCommon();
            short version 
              = fVersionDetector.determineDocVersion(fInputSource);
            configurePipeline();
            reset();
            fConfigUpdated = false;
            fVersionDetector.startDocumentParsing(
              (XMLEntityHandler) fCurrentScanner, version);
            fInputSource = null;
        }

        return fCurrentScanner.scanDocument(complete); 

    } 

}
/*
 * Copyright (c) 2011-2011 Polly Enterprises Pty Ltd and/or its affiliates.
 *  All rights reserved. This code is not to be distributed in binary
 * or source form without express consent of Polly Enterprises Pty Ltd.
 *
 *
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package au.com.polly.util.csv;

import java.util.HashMap;

/**
  * Implement this interface if you wish to process data from the
  * CSV parser.
  *
  *
  */
public interface ParserListener
{

/**
  * @param parser the CSV parser which invoked this method. can be called
  *    to obtain data such as the file and line number being processed.
  * @param data hashmap, the key into which is the field name within the
  *		record. A <b><i>null</i></b> value will be passed as a dummy
  *		value to indicate either the file parser has finished, or that
  *		an error was encountered processing this record.
  * @param auxillary chunk of data that was specified to the file parser
  *    when this listener was added. this may be null if no data was specified.
  * @param error this flag indicates whether or not an error condition
  *	   was generated whilst processing this record, this would probably be
  *	   due to one or more missing fields, but can be checked by
  *	   interrogating the <a href="Parser.html#getError">getError()</a>
  *		method of the parser object.
  *
  */
public void processRecord(Parser parser, HashMap data, Object auxillary, boolean error);

}

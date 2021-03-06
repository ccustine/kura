/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package org.eclipse.kura.web.client.util;

import java.util.MissingResourceException;

import org.eclipse.kura.web.client.messages.ValidationMessages;

import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ValidationState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

public class TextFieldValidator {

	private static final ValidationMessages MSGS = GWT.create(ValidationMessages.class);

	TextBox Tbox;
	FormGroup group;
	FieldType type;
	boolean required;

	public void validate( TextBox textBox,FormGroup formGroup, FieldType fieldType,  boolean req){		
		type=fieldType;
		required=req;
		group=formGroup;
		
		textBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				//value format
				TextBox box = (TextBox)event.getSource();

				if(!box.getText().matches(type.getRegex())){
					group.setValidationState(ValidationState.ERROR);
					box.setPlaceholder(type.getRegexMessage());
				}else{
					group.setValidationState(ValidationState.NONE);
					box.setPlaceholder("");
				}
				//value required
				if(required && ("".equals(box.getText().trim()) || box.getText()==null)){
					group.setValidationState(ValidationState.ERROR);
					box.setPlaceholder(type.getRequiredMessage());
				}else{
					group.setValidationState(ValidationState.NONE);
					box.setPlaceholder("");
				}
			}});
	}
	

	public enum FieldType {
		
		SIMPLE_NAME		("simple_name",  "^[a-zA-Z0-9\\-]{3,}$"),
		NAME            ("name",         "^[a-zA-Z0-9\\_\\-]{3,}$"),
		NAME_SPACE      ("name_space",   "^[a-zA-Z0-9\\ \\_\\-]{3,}$"),
		PASSWORD	    ("password",     "^.*(?=.{6,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!\\~\\|]).*$"), 
		EMAIL		    ("email",        "^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$"), 
		PHONE           ("phone",        "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$"), 
		ALPHABET        ("alphabet",     "^[a-zA-Z_]+$"), 
		ALPHANUMERIC    ("alphanumeric", "^[a-zA-Z0-9_]+$"), 
		NUMERIC         ("numeric",      "^[+0-9.]+$"),
		NETWORK			("network",      "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})"),
		IPv4_ADDRESS	("ipv4_address", "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b"),
		PORT_RANGE		("port_range", "^[0-9]+:*[0-9]+$"),
		MAC_ADDRESS		("mac_address", "^([0-9a-fA-F]{2}:){5}([0-9a-fA-F]{2})$");
		
		private String m_name;
		private String m_regex;
		private String m_regexMsg;
		private String m_toolTipMsg;
		private String m_requiredMsg;

		FieldType(String name, String regex) {
			
			m_name = name;
			m_regex = regex;
			m_regexMsg = name + "RegexMsg";
			m_toolTipMsg = name + "ToolTipMsg";
			m_requiredMsg = name + "RequiredMsg";
		}


		public String getName() {
			return m_name;
		}
		
		public String getRegex() {
			return m_regex;
		}

		public String getRegexMessage() {
			try {
				return MSGS.getString(m_regexMsg);
			}
			catch (MissingResourceException mre) {
				return null;
			}
		}

		public String getToolTipMessage() {
			try {
				return MSGS.getString(m_toolTipMsg);
			}
			catch (MissingResourceException mre) {
				return null;
			}
		}

		public String getRequiredMessage() {
			try {
				return MSGS.getString(m_requiredMsg);
			}
			catch (MissingResourceException mre) {
				return null;
			}
		}
	}
}

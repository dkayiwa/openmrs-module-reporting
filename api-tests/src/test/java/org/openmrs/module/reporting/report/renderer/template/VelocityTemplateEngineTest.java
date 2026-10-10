/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.reporting.report.renderer.template;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Report designs were written against Velocity 1.7, so each test pins one of the settings that keep Velocity 2
 * rendering them the way 1.7 did. The expected values are what Velocity 1.7 renders for the same template.
 */
public class VelocityTemplateEngineTest {

	private String evaluate(String template, Map<String, Object> bindings) throws Exception {
		return new VelocityTemplateEngine().evaluate(template, bindings);
	}

	private String evaluate(String template) throws Exception {
		return evaluate(template, new HashMap<String, Object>());
	}

	@Test
	public void evaluate_shouldResolveBindingsWithDotsInTheirNames() throws Exception {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("report.name", "Test Report");
		assertEquals("Test Report", evaluate("$report-name", bindings));
	}

	@Test
	public void evaluate_shouldGobbleWhitespaceAroundDirectivesAsVelocity17Did() throws Exception {
		assertEquals("      in\n  out\n", evaluate("#set($a = 1)\n  #if($a == 1)\n    in\n  #end\nout\n"));
	}

	@Test
	public void evaluate_shouldTreatAnEmptyCollectionAsTrueInAnIf() throws Exception {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("empty", new ArrayList<Object>());
		assertEquals("true", evaluate("#if($empty)true#{else}false#end", bindings));
	}

	@Test
	public void evaluate_shouldRenderANullMacroArgumentAsTheCallersReference() throws Exception {
		assertEquals("[$nothing]", evaluate("#macro(show $x)[$x]#end#show($nothing)"));
	}

	@Test
	public void evaluate_shouldNotConvertMethodArguments() throws Exception {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("s", "abc");
		assertEquals("$s.charAt('1')", evaluate("$s.charAt('1')", bindings));
	}

	@Test
	public void evaluate_shouldAllowARangeToBeModified() throws Exception {
		assertEquals("[1, 2, 3, 4]", evaluate("#set($r = [1..3])#set($ignore = $r.add(4))$r"));
	}
}

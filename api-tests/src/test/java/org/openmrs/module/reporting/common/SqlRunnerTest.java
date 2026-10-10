package org.openmrs.module.reporting.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlRunnerTest {

    @Test
    public void parseParametersIntoStatements_shouldEscapeParametersWithSingleQuotes() throws Exception {
        SqlRunner sqlRunner = new SqlRunner(null);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("generatedBy", "Fredrick 'Fred' Flintstone");
        List<String> results = sqlRunner.parseParametersIntoStatements(parameters);
        Assertions.assertEquals("set @generatedBy='Fredrick ''Fred'' Flintstone'", results.get(0));
    }

    /**
     * The delimiter pattern as it stood before its nested quantifier ([^\\s]+)+ was collapsed to (\\S+).
     * Kept here only as the reference the production pattern must agree with, line for line.
     */
    private static final Pattern PREVIOUS_DELIMITER_PATTERN =
            Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", Pattern.CASE_INSENSITIVE);

    private static final List<String> SQL_LINES = Arrays.asList(
            "delimiter $$", "DELIMITER //", "Delimiter ;", "delimiter=$$", "delimiter = $$", "  delimiter   $$   ",
            "-- delimiter $$", "--delimiter $$", "  --  DELIMITER  ##  ", "delimiter $$ trailing comment",
            "delimiter $$ -- comment", "delimiter\t$$", "\tdelimiter\t=\t//\t", "delimiter $$\n", "delimiter $$\r\n",
            "delimiter", "delimiter ", "delimiter =", "delimiter = ", "delimiters $$", "delimiter=", "xdelimiter $$",
            "select * from patient;", "select 'delimiter $$' from dual;", "-- just a comment", "", "   ",
            "create procedure p() begin select 1; end$$", "-- -- delimiter $$", "delimiter " + (char) 0x85 + (char) 0x85,
            "delimiter $$" + (char) 0x2028, "delimiter a" + (char) 0x85 + "x", "delimiter aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + (char) 0x85 + "x",
            "delimiter $$\n\nselect 1", "DELIMITER ;;", "delimiter ===", "/* delimiter $$ */", "insert into t values ('a');");

    @Test
    public void getNewDelimiter_shouldReturnTheDelimiterTokenTheLineDeclares() {
        SqlRunner sqlRunner = new SqlRunner(null);
        Assertions.assertEquals("$$", sqlRunner.getNewDelimiter("delimiter $$"));
        Assertions.assertEquals("//", sqlRunner.getNewDelimiter("  DELIMITER  //  "));
        Assertions.assertEquals("$$", sqlRunner.getNewDelimiter("delimiter=$$"));
        Assertions.assertEquals("$$", sqlRunner.getNewDelimiter("-- delimiter = $$ trailing comment"));
        Assertions.assertEquals("=", sqlRunner.getNewDelimiter("delimiter ="));
        Assertions.assertNull(sqlRunner.getNewDelimiter("delimiter"));
        Assertions.assertNull(sqlRunner.getNewDelimiter("select * from patient;"));
        Assertions.assertNull(sqlRunner.getNewDelimiter("delimiter aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + (char) 0x85 + "x"));
    }

    @Test
    public void getNewDelimiter_shouldAgreeWithThePreviousPatternOnEveryLine() {
        SqlRunner sqlRunner = new SqlRunner(null);
        for (String line : SQL_LINES) {
            Matcher previous = PREVIOUS_DELIMITER_PATTERN.matcher(line);
            String expected = previous.matches() ? previous.group(2) : null;
            Assertions.assertEquals(expected, sqlRunner.getNewDelimiter(line), "line: [" + line + "]");
        }
    }

}

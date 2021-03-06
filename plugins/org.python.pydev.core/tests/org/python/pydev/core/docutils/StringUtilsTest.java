/*
 * Created on 03/09/2005
 */
package org.python.pydev.core.docutils;

import java.util.Arrays;

import org.python.pydev.core.Tuple;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

    public static void main(String[] args) {
        try {
            StringUtilsTest test = new StringUtilsTest();
            test.setUp();
            test.testRemoveWhitespaceColumnsToLeftAndApplyIndent();
            test.tearDown();
            junit.textui.TestRunner.run(StringUtilsTest.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testFormat() {
        assertEquals("teste", StringUtils.format("%s", new Object[]{"teste"}));
        assertEquals("teste 1", StringUtils.format("%s 1", new Object[]{"teste"}));
        assertEquals("%", StringUtils.format("%", new Object[]{}));
    }
    
    public void testStripExt() {
        assertEquals("teste", StringUtils.stripExtension("teste.aaa"));
        assertEquals("teste.aaa", StringUtils.stripExtension("teste.aaa.bbb"));
        assertEquals("teste", StringUtils.stripExtension("teste"));
        assertEquals("a", StringUtils.stripExtension("a.a"));
        assertEquals("", StringUtils.stripExtension(""));
    }
    
    
    public void testReplaceAllSlashes() throws Exception {
        assertEquals("foo", StringUtils.replaceAllSlashes("foo"));
        assertEquals("foo/", StringUtils.replaceAllSlashes("foo\\"));
        assertEquals("/foo/", StringUtils.replaceAllSlashes("\\foo\\"));
        assertEquals("/foo///", StringUtils.replaceAllSlashes("\\foo\\\\\\"));
        
    }
    
    public void testReplaceAll() throws Exception {
        assertEquals("foo", StringUtils.replaceAll("fjj", "j", "o"));
        assertEquals("fok", StringUtils.replaceAll("fkkkk", "kkk", "o"));
        assertEquals("foo", StringUtils.replaceAll("fkkkk", "kk", "o"));
        assertEquals("kkkkkkkkk", StringUtils.replaceAll("www", "w", "kkk"));
        assertEquals("www", StringUtils.replaceAll("www", "o", "a"));
        
        String initial = ""+
        "import sys; sys.ps1=''; sys.ps2=''\r\n"+
        "print >> sys.stderr, 'PYTHONPATH:'\r\n"+
        "for p in sys.path:\r\n"+
        "    print >> sys.stderr,  p\r\n" +
        "\r\n" +                                                //to finish the for scope
        "print >> sys.stderr, 'Ok, all set up... Enjoy'\r\n"+
        "";
        assertEquals(initial, StringUtils.replaceAll(initial, "\r\n", "\r\n"));
        
        String expected = ""+
        "import sys; sys.ps1=''; sys.ps2=''\r"+
        "print >> sys.stderr, 'PYTHONPATH:'\r"+
        "for p in sys.path:\r"+
        "    print >> sys.stderr,  p\r" +
        "\r" +                                                //to finish the for scope
        "print >> sys.stderr, 'Ok, all set up... Enjoy'\r"+
        "";
        assertEquals(expected, StringUtils.replaceAll(initial, "\r\n", "\r"));
    }
    
    public void testRemoveWhitespaceColumnsToLeft() throws Exception {
        assertEquals("foo", StringUtils.removeWhitespaceColumnsToLeft("   foo"));
        assertEquals("foo\n", StringUtils.removeWhitespaceColumnsToLeft("   foo\n"));
        assertEquals("foo\n   foo\n", StringUtils.removeWhitespaceColumnsToLeft(" foo\n    foo\n"));
    }
    
    public void testTrim() throws Exception {
        assertEquals("  foo", StringUtils.rightTrim("  foo  "));
        assertEquals("foo  ", StringUtils.leftTrim("  foo  "));
        assertEquals("\t\tfoo", StringUtils.rightTrim("\t\tfoo\t\t"));
        assertEquals("foo\t\t", StringUtils.leftTrim("\t\tfoo\t\t"));
        
    }
    public void testFixWhitespaceColumnsToLeftFromDocstring() throws Exception {
        assertEquals("foo", StringUtils.fixWhitespaceColumnsToLeftFromDocstring("foo", "    "));
        assertEquals("\n    foo", StringUtils.fixWhitespaceColumnsToLeftFromDocstring("\nfoo", "    "));
        assertEquals("\n    foo\n    ", StringUtils.fixWhitespaceColumnsToLeftFromDocstring("\nfoo\n", "    "));
        assertEquals("\n    \n    foo\n    ", StringUtils.fixWhitespaceColumnsToLeftFromDocstring("\n\nfoo\n", "    "));
    }
    
    public void testSplitOn1st() throws Exception {
        assertEquals(new Tuple<String, String>("aa", "bb.cc"), StringUtils.splitOnFirst("aa.bb.cc", '.'));
        assertEquals(new Tuple<String, String>("aa_bb_cc", ""), StringUtils.splitOnFirst("aa_bb_cc", '.'));
        
        assertEquals(new Tuple<String, String>("aa", "bb.cc"), StringUtils.splitOnFirst("aa<TAG>bb.cc", "<TAG>"));
        assertEquals(new Tuple<String, String>("aa_bb_cc", ""), StringUtils.splitOnFirst("aa_bb_cc", "TAG"));
        
    }
    
    public void testSplit() throws Exception{
        String[] split = StringUtils.split("aaa bb  ", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("  aaa  bb   ", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("aaa  bb", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("aaa  bb  ", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("aaa ", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split(" aaa", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split("aaa", ' ').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split("aaa bb\tccc\nbb ", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb", "ccc", "bb"}, split));
        
        split = StringUtils.split("aaa bb\t\t ccc\nbb ", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb", "ccc", "bb"}, split));
        
        split = StringUtils.split("aaa bb\t \n", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("aaa \t\nbb\t \n", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "bb"}, split));
        
        split = StringUtils.split("aaa", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split("aaa\t\n ", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split("\t\n  aaa\t\n ", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split("\t\n  aaa", ' ', '\t', '\n').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.split(" aaa   ", new char[]{' '}).toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa"}, split));
        
        split = StringUtils.splitAndRemoveEmptyTrimmed("|| |a||b||", '|').toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"a", "b"}, split));
    }
    
    
    public void testSplitOnString() throws Exception {
        String[] split = StringUtils.split("aaa bb ccc bb kkk bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "ccc", "kkk"}, split));
        
        split = StringUtils.split("aaa bb ccc bb kkk bb", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "ccc", "kkk bb"}, split));
        
        split = StringUtils.split("aaa bb ccc bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "ccc"}, split));
        
        split = StringUtils.split(" bb aaa bb ccc bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"aaa", "ccc"}, split));
        
        split = StringUtils.split(" bb  bb ccc bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"ccc"}, split));
        
        split = StringUtils.split("ccc", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"ccc"}, split));
        
        split = StringUtils.split("", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{}, split));
        
        split = StringUtils.split("a", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"a"}, split));
        
        split = StringUtils.split(" bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{}, split));
        
        split = StringUtils.split(" bb b", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"b"}, split));
        
        split = StringUtils.split(" bb b bb", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"b bb"}, split));
        
        split = StringUtils.split(" bb b  bb ", " bb ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{"b "}, split));
        
        split = StringUtils.split(" bb b  bb ", " bb2 ").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{" bb b  bb "}, split));
        
        split = StringUtils.split(" bb b  bb ", "b").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{" ", " ", "  ", " "}, split));
        
        split = StringUtils.split(" bb bb  bb ", "bb").toArray(new String[0]);
        assertTrue(Arrays.equals(new String[]{" ", " ", "  ", " "}, split));
    }
    
    public void testReplaceChars() throws Exception {
        assertEquals("aaaXeeeXcccX", StringUtils.replaceNewLines("aaa\neee\r\nccc\r", "X"));
        assertEquals("aaabbbccc", StringUtils.removeNewLineChars("aaa\r\nbbb\rccc\n"));
    }
    
    public void testCodingStd() throws Exception {
        assertEquals("a_b_c", StringUtils.asStyleLowercaseUnderscores("a_b_c"));
        assertEquals("a_and_b", StringUtils.asStyleLowercaseUnderscores("aAndB"));
        assertEquals("abc", StringUtils.asStyleLowercaseUnderscores("ABC"));
        assertEquals("a_b_c", StringUtils.asStyleLowercaseUnderscores("A_B_C"));
        assertEquals("a_bd_c", StringUtils.asStyleLowercaseUnderscores("A_BD_C"));
        assertEquals("my_camel_call", StringUtils.asStyleLowercaseUnderscores("MyCamelCall"));
        assertEquals("__a__b__c__", StringUtils.asStyleLowercaseUnderscores("__a__b__c__"));
        assertEquals("__a__b__c__", StringUtils.asStyleLowercaseUnderscores("__a__B__c__"));
        assertEquals("__a_b__b__c__", StringUtils.asStyleLowercaseUnderscores("__aB__B__c__"));
        assertEquals("", StringUtils.asStyleLowercaseUnderscores(""));
        assertEquals("a", StringUtils.asStyleLowercaseUnderscores("a"));
        assertEquals("a", StringUtils.asStyleLowercaseUnderscores("A"));
        assertEquals("aa", StringUtils.asStyleLowercaseUnderscores("AA"));
        assertEquals("aab", StringUtils.asStyleLowercaseUnderscores("AAb"));
        assertEquals("-*&()1", StringUtils.asStyleLowercaseUnderscores("-*&()1"));
        assertEquals("my_constant", StringUtils.asStyleLowercaseUnderscores("MY_CONSTANT"));
        
        
        assertEquals("myCamelCall", StringUtils.asStyleCamelCaseFirstLower("MyCamelCall"));
        assertEquals("myLowerCall", StringUtils.asStyleCamelCaseFirstLower("my_lower_call"));
        assertEquals("__myLowerCall__", StringUtils.asStyleCamelCaseFirstLower("__my__lower__call__"));
        assertEquals("__myLOowerCall__", StringUtils.asStyleCamelCaseFirstLower("__my__lOower__call__"));
        assertEquals("", StringUtils.asStyleCamelCaseFirstLower(""));
        assertEquals("a", StringUtils.asStyleCamelCaseFirstLower("a"));
        assertEquals("a", StringUtils.asStyleCamelCaseFirstLower("A"));
        assertEquals("ab", StringUtils.asStyleCamelCaseFirstLower("Ab"));
        assertEquals("myConstant", StringUtils.asStyleCamelCaseFirstLower("MY_CONSTANT"));
        
        assertEquals("Ab", StringUtils.asStyleCamelCaseFirstUpper("Ab"));
        assertEquals("", StringUtils.asStyleCamelCaseFirstUpper(""));
        assertEquals("A", StringUtils.asStyleCamelCaseFirstUpper("a"));
        assertEquals("AB", StringUtils.asStyleCamelCaseFirstUpper("a_b"));
        assertEquals("ABc", StringUtils.asStyleCamelCaseFirstUpper("a_bc"));
        assertEquals("-*&()1", StringUtils.asStyleCamelCaseFirstUpper("-*&()1"));
        assertEquals("MyConstant", StringUtils.asStyleCamelCaseFirstUpper("MY_CONSTANT"));
        
    }
    
    public void testRemoveWhitespaceColumnsToLeftAndApplyIndent() {
		assertEquals("    a=10\n#comment", StringUtils.removeWhitespaceColumnsToLeftAndApplyIndent("a=10\n#comment", "    ", false));
		assertEquals("    a=10\n#comment\n    b=30", StringUtils.removeWhitespaceColumnsToLeftAndApplyIndent("a=10\n#comment\nb=30", "    ", false));
		assertEquals("    a=10\n    #comment", StringUtils.removeWhitespaceColumnsToLeftAndApplyIndent("a=10\n#comment", "    ", true));
		assertEquals("    a=10\n    #comment\n    b=30", StringUtils.removeWhitespaceColumnsToLeftAndApplyIndent("a=10\n#comment\nb=30", "    ", true));
		assertEquals("    a=10\n    \n    b=30", StringUtils.removeWhitespaceColumnsToLeftAndApplyIndent("    a=10\n\n    b=30", "    ", true));
	}
}


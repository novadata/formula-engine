package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.ExpressionFactory;
import com.novacloud.data.formula.impl.ExpressionFactoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtmlFunctionsTest {
    private static ExpressionFactory factory = new ExpressionFactoryImpl();

    @Test
    public void testAttr() throws Exception {
        String formula = "attr('<div name=divname ></div>','name')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("divname", actual);
        formula = "attr('<div name=divname ></div><div name=div2></div>','name')";
        actual = factory.create(formula).evaluate(this);
        assertEquals("divname", actual);
    }

    @Test
    public void testTagName() throws Exception {
        String formula = "tagname('<div name=divname ></div>')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("div", actual);
        formula = "tagname('<div name=divname ></div><span>span</span>')";
        actual = factory.create(formula).evaluate(this);
        assertEquals("div", actual);
    }

    @Test
    public void testText() throws Exception {
        String formula = "text('<div name=divname ><a>a</a>div text</div>')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("adiv text", actual);

        formula = "text('<div name=divname ><a>a</a>div text</div><span>span</span>')";
        actual = factory.create(formula).evaluate(this);
        assertEquals("adiv textspan", actual);
    }

    @Test
    public void testOwnText() throws Exception {
        String formula = "owntext('<div name=divname ><a>a</a>div text</div>')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("div text", actual);
        formula = "owntext('<div name=divname ><a>a</a>div text</div><span>span</span>')";
        actual = factory.create(formula).evaluate(this);
        assertEquals("div text", actual);
    }

    @Test
    public void testInnerHtml() throws Exception {
        String formula = "innerhtml('<div name=divname ><a>a</a>div text</div>')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("<a>a</a>div text", actual);
        formula = "innerhtml('<div name=divname ><a>a</a>div text</div><span>span</span>')";
        actual = factory.create(formula).evaluate(this);
        assertEquals("<a>a</a>div text", actual);
    }

    @Test
    public void testChild() throws Exception {
        String formula = "child('<div name=divname ><a>a</a><span>span</span>div text</div>',0)";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("<a>a</a>", actual);
        formula = "child('<div name=divname ><a>a</a><span>span</span>div text</div><span>span</span>',0)";
        actual = factory.create(formula).evaluate(this);
        assertEquals("<a>a</a>", actual);
    }

    @Test
    public void testQuerySelector() throws Exception {
        String formula = "querySelector('<div name=divname ><a>a1</a><a>a2</a><span>span</span>div text</div>','a')";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("<a>a1</a>", actual);
    }

    @Test
    public void testSibling() throws Exception {
        String  formula = "sibling('<div name=divname ><a>a</a><span>span1</span>div text</div><span>span2</span>',1)";
        Object actual = factory.create(formula).evaluate(this);
        assertEquals("<span>span2</span>", actual);
    }

    @Test
    public void testQuerySeletorAll() throws Exception {
        String  formula = "querySelectorAll('<div name=divname ><a>a</a><span>span1</span>div text</div><span>span2</span>','span')";
             Object actual = factory.create(formula).evaluate(this);
             assertEquals("<span>span1</span>\n<span>span2</span>", actual);
    }
}
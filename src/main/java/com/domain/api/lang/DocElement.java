package com.domain.api.lang;

import org.dom4j.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by pei hao on 2021/9/11.
 */
public class DocElement implements Element{


    @Override
    public QName getQName() {
        return null;
    }

    @Override
    public void setQName(QName qname) {

    }

    @Override
    public Namespace getNamespace() {
        return null;
    }

    @Override
    public QName getQName(String qualifiedName) {
        return null;
    }

    @Override
    public Namespace getNamespaceForPrefix(String prefix) {
        return null;
    }

    @Override
    public Namespace getNamespaceForURI(String uri) {
        return null;
    }

    @Override
    public List<Namespace> getNamespacesForURI(String uri) {
        return null;
    }

    @Override
    public String getNamespacePrefix() {
        return null;
    }

    @Override
    public String getNamespaceURI() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    @Override
    public List<Namespace> additionalNamespaces() {
        return null;
    }

    @Override
    public List<Namespace> declaredNamespaces() {
        return null;
    }

    @Override
    public Element addAttribute(String name, String value) {
        return null;
    }

    @Override
    public Element addAttribute(QName qName, String value) {
        return null;
    }

    @Override
    public Element addComment(String comment) {
        return null;
    }

    @Override
    public Element addCDATA(String cdata) {
        return null;
    }

    @Override
    public Element addEntity(String name, String text) {
        return null;
    }

    @Override
    public Element addNamespace(String prefix, String uri) {
        return null;
    }

    @Override
    public Element addProcessingInstruction(String target, String text) {
        return null;
    }

    @Override
    public Element addProcessingInstruction(String target, Map<String, String> data) {
        return null;
    }

    @Override
    public Element addText(String text) {
        return null;
    }

    @Override
    public void add(Attribute attribute) {

    }

    @Override
    public void add(CDATA cdata) {

    }

    @Override
    public void add(Entity entity) {

    }

    @Override
    public void add(Text text) {

    }

    @Override
    public void add(Namespace namespace) {

    }

    @Override
    public boolean remove(Attribute attribute) {
        return false;
    }

    @Override
    public boolean remove(CDATA cdata) {
        return false;
    }

    @Override
    public boolean remove(Entity entity) {
        return false;
    }

    @Override
    public boolean remove(Namespace namespace) {
        return false;
    }

    @Override
    public boolean remove(Text text) {
        return false;
    }

    @Override
    public boolean supportsParent() {
        return false;
    }

    @Override
    public Element getParent() {
        return null;
    }

    @Override
    public void setParent(Element parent) {

    }

    @Override
    public Document getDocument() {
        return null;
    }

    @Override
    public void setDocument(Document document) {

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean hasContent() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setText(String text) {

    }

    @Override
    public String getTextTrim() {
        return "";
    }

    @Override
    public String getStringValue() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getPath(Element context) {
        return null;
    }

    @Override
    public String getUniquePath() {
        return null;
    }

    @Override
    public String getUniquePath(Element context) {
        return null;
    }

    @Override
    public String asXML() {
        return null;
    }

    @Override
    public void write(Writer writer) throws IOException {

    }

    @Override
    public short getNodeType() {
        return 0;
    }

    @Override
    public String getNodeTypeName() {
        return null;
    }

    @Override
    public Node detach() {
        return null;
    }

    @Override
    public List<Node> selectNodes(String xpathExpression) {
        return null;
    }

    @Override
    public Object selectObject(String xpathExpression) {
        return null;
    }

    @Override
    public List<Node> selectNodes(String xpathExpression, String comparisonXPathExpression) {
        return null;
    }

    @Override
    public List<Node> selectNodes(String xpathExpression, String comparisonXPathExpression, boolean removeDuplicates) {
        return null;
    }

    @Override
    public Node selectSingleNode(String xpathExpression) {
        return null;
    }

    @Override
    public String valueOf(String xpathExpression) {
        return null;
    }

    @Override
    public Number numberValueOf(String xpathExpression) {
        return null;
    }

    @Override
    public boolean matches(String xpathExpression) {
        return false;
    }

    @Override
    public XPath createXPath(String xpathExpression) throws InvalidXPathException {
        return null;
    }

    @Override
    public Node asXPathResult(Element parent) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public List<Attribute> attributes() {
        return null;
    }

    @Override
    public void setAttributes(List<Attribute> attributes) {

    }

    @Override
    public int attributeCount() {
        return 0;
    }

    @Override
    public Iterator<Attribute> attributeIterator() {
        return null;
    }

    @Override
    public Attribute attribute(int index) {
        return null;
    }

    @Override
    public Attribute attribute(String name) {
        return null;
    }

    @Override
    public Attribute attribute(QName qName) {
        return null;
    }

    @Override
    public String attributeValue(String name) {
        return null;
    }

    @Override
    public String attributeValue(String name, String defaultValue) {
        return null;
    }

    @Override
    public String attributeValue(QName qName) {
        return null;
    }

    @Override
    public String attributeValue(QName qName, String defaultValue) {
        return null;
    }

    @Override
    public void setAttributeValue(String name, String value) {

    }

    @Override
    public void setAttributeValue(QName qName, String value) {

    }

    @Override
    public Element element(String name) {
        return null;
    }

    @Override
    public Element element(QName qName) {
        return null;
    }

    @Override
    public List<Element> elements() {
        return null;
    }

    @Override
    public List<Element> elements(String name) {
        return null;
    }

    @Override
    public List<Element> elements(QName qName) {
        return null;
    }

    @Override
    public Iterator<Element> elementIterator() {
        return null;
    }

    @Override
    public Iterator<Element> elementIterator(String name) {
        return null;
    }

    @Override
    public Iterator<Element> elementIterator(QName qName) {
        return null;
    }

    @Override
    public boolean isRootElement() {
        return false;
    }

    @Override
    public boolean hasMixedContent() {
        return false;
    }

    @Override
    public boolean isTextOnly() {
        return false;
    }

    @Override
    public void appendAttributes(Element element) {

    }

    @Override
    public Element createCopy() {
        return null;
    }

    @Override
    public Element createCopy(String name) {
        return null;
    }

    @Override
    public Element createCopy(QName qName) {
        return null;
    }

    @Override
    public String elementText(String name) {
        return null;
    }

    @Override
    public String elementText(QName qname) {
        return null;
    }

    @Override
    public String elementTextTrim(String name) {
        return null;
    }

    @Override
    public String elementTextTrim(QName qname) {
        return null;
    }

    @Override
    public Node getXPathResult(int index) {
        return null;
    }

    @Override
    public Node node(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public int indexOf(Node node) {
        return 0;
    }

    @Override
    public int nodeCount() {
        return 0;
    }

    @Override
    public Element elementByID(String elementID) {
        return null;
    }

    @Override
    public List<Node> content() {
        return null;
    }

    @Override
    public Iterator<Node> nodeIterator() {
        return null;
    }

    @Override
    public void setContent(List<Node> content) {

    }

    @Override
    public void appendContent(Branch branch) {

    }

    @Override
    public void clearContent() {

    }

    @Override
    public List<ProcessingInstruction> processingInstructions() {
        return null;
    }

    @Override
    public List<ProcessingInstruction> processingInstructions(String target) {
        return null;
    }

    @Override
    public ProcessingInstruction processingInstruction(String target) {
        return null;
    }

    @Override
    public void setProcessingInstructions(List<ProcessingInstruction> listOfPIs) {

    }

    @Override
    public Element addElement(String name) {
        return null;
    }

    @Override
    public Element addElement(QName qname) {
        return null;
    }

    @Override
    public Element addElement(String qualifiedName, String namespaceURI) {
        return null;
    }

    @Override
    public boolean removeProcessingInstruction(String target) {
        return false;
    }

    @Override
    public void add(Node node) {

    }

    @Override
    public void add(Comment comment) {

    }

    @Override
    public void add(Element element) {

    }

    @Override
    public void add(ProcessingInstruction pi) {

    }

    @Override
    public boolean remove(Node node) {
        return false;
    }

    @Override
    public boolean remove(Comment comment) {
        return false;
    }

    @Override
    public boolean remove(Element element) {
        return false;
    }

    @Override
    public boolean remove(ProcessingInstruction pi) {
        return false;
    }

    @Override
    public void normalize() {

    }
}

package com.adrop.dropbox.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.adrop.dropbox.common.mapping.ActionMapping;
import com.adrop.dropbox.common.mapping.PageMapping;

public class ActionFilter implements Filter {
	private static final String KEY_METHOD = "method";
	private static final String KEY_CLASS = "class";
	private static final String KEY_ACTION = "action";
	private static final String KEY_TYPE = "type";
	private static final String KEY_TO = "to";
	private static final String KEY_PATH = "path";
	private static final String KEY_POST = "post";
	private static final String KEY_GET = "get";
	private static final String KEY_MAPPING = "mapping";
	private static final String KEY_PACKAGE = "package";
	private static final String KEY_CONFIG_LOCATION = "configLocation";

	private static final Logger logger = Logger.getLogger(ActionFilter.class.getName());

	private Map<String, Mapping> getMappings = new HashMap<String, Mapping>();
	private Map<String, Mapping> postMappings = new HashMap<String, Mapping>();

	public void destroy() {
		getMappings.clear();
		postMappings.clear();
		getMappings = null;
		postMappings = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)	throws IOException,
																							ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) resp;
		String servletPath = httpRequest.getServletPath();
		if (getMappings.containsKey(servletPath)) {
			doGet(httpRequest, httpResponse);
		} else if (postMappings.containsKey(servletPath)) {
			doPost(httpRequest, httpResponse);
		} else {
			filterChain.doFilter(httpRequest, httpResponse);
		}

	}

	public void init(FilterConfig config) throws ServletException {
		ServletContext ctx = config.getServletContext();
		String configLocation = ctx.getInitParameter(KEY_CONFIG_LOCATION);
		String configPath = ctx.getRealPath(configLocation);
		DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
			Document doc = domBuilder.parse(configPath);
			Element documentElement = doc.getDocumentElement();
			String commandPackage = getAttribute(documentElement, KEY_PACKAGE);
			if (KEY_MAPPING.equals(documentElement.getTagName())) {
				NodeList nodes = documentElement.getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) node;
						if (KEY_GET.equals(el.getTagName())) {
							parseMethodElement(commandPackage, el, getMappings);
						} else if (KEY_POST.equals(el.getTagName())) {
							parseMethodElement(commandPackage, el, postMappings);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			ctx.log(null, e);
		} catch (SAXException e) {
			ctx.log(null, e);
		} catch (IOException e) {
			ctx.log(null, e);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		processRequest(req, resp, getMappings);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		processRequest(req, resp, postMappings);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp, Map<String, Mapping> mappings) throws IOException,
																												ServletException {
		String servletPath = req.getServletPath();
		try {
			if (mappings.containsKey(servletPath)) {
				Context ctx = createContext(req, resp);
				Command cmd = mappings.get(servletPath).createCommand();
				if (cmd != null) {
					Model model = cmd.execute(ctx);
					if (model != null) {
						model.view(req, resp);
					}
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, servletPath);
				}
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, servletPath);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, null, e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private Context createContext(HttpServletRequest req, HttpServletResponse resp) {
		return new Context(req, resp);
	}

	/**
	 * 
	 * Parse get and post element, which configures the process of get or post request method
	 * 
	 * @param commandPackage
	 * @param el
	 * @param mappings
	 */
	private void parseMethodElement(String commandPackage, Element el, Map<String, Mapping> mappings) {
		String reqPath = getAttribute(el, KEY_PATH);
		NodeList tos = el.getElementsByTagName(KEY_TO);
		if (tos.getLength() == 1) {
			Element to = (Element) tos.item(0);
			String type = getAttribute(to, KEY_TYPE); // redirect or forward, default is forward
			NodeList actions = to.getElementsByTagName(KEY_ACTION);
			if (actions.getLength() == 1) {
				Element action = (Element) actions.item(0);
				String className = getAttribute(action, KEY_CLASS);
				String method = getAttribute(action, KEY_METHOD);
				// If the class name is not qualified name, 
				if (!className.startsWith(commandPackage)) {
					className = commandPackage + "." + className;
				}
				// ignore type attribute of the action element
				mappings.put(reqPath, new ActionMapping(reqPath, className, method));
			} else if (actions.getLength() == 0) {
				String targetPage = getTextContent(to);
				mappings.put(reqPath, new PageMapping(targetPage, type));
			}
		} else {
			// Do nothing if no to element
		}
	}

	private String getAttribute(Element to, String attrName, String defaultValue) {
		String v = null;
		if (to.hasAttribute(attrName)) {
			v = to.getAttribute(attrName);
		}
		return v == null ? defaultValue : v;
	}

	private String getTextContent(Element el) {
		return el.getTextContent();
	}

	private String getAttribute(Element to, String attrName) {
		return getAttribute(to, attrName, null);
	}

}

package com.program.rebuildSQL;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**

 * @author wendy926

 */
class MongodbReadServlet extends HttpServlet {
    private Logger logger = LogManager.getLogger(MongodbReadServlet.class
            .getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String collection = request.getParameter("MongoCollection");
        String mode = request.getParameter("mode");
        String MongoDbname = request.getParameter("MongoDbname");  
        RebuildService rebuildService = new RebuildService(mode);
        boolean rebuild = rebuildService.rebuildMongoESInflux("mongoDB",MongoDbname, collection);
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>" +
        "            <html>" +
        "            <head>" +
        "                <title>Servlet MongodbHtml</title>" +
        "            </head>" +
        "            <body>");
        if(rebuild == true) {
            out.println(
        "            <br>" + MongoDbname +
        "            <br>" + collection +
        "            <br>" + mode);
        }
        else {
            out.println("RebuildService.getErrorMsg()");
        }
        out.println("</body>" +
        "            </html>");

    }
}

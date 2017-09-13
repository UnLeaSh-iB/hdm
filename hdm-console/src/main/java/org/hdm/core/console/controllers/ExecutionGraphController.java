package org.hdm.core.console.controllers;

import org.hdm.akka.server.SmsSystem;
import org.hdm.core.console.models.DagGraph;
import org.hdm.core.console.views.HDMViewAdapter;
import org.hdm.core.messages.ExecutionTraceQuery;
import org.hdm.core.messages.ExecutionTraceResp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tiantian on 10/04/16.
 */
public class ExecutionGraphController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exeId = req.getParameter("executionTag");
        ExecutionTraceQuery msg = new ExecutionTraceQuery(exeId);
        String actor = AbstractController.masterExecutor;
        ExecutionTraceResp res = (ExecutionTraceResp) SmsSystem.askSync(actor, msg).get();
        DagGraph vo = HDMViewAdapter.executionTraceToGraph(res);
        String json = ObjectUtils.objectToJson(vo);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}

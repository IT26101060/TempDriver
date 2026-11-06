package lk.drivingschool.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lk.drivingschool.util.FileStorage;
import lk.drivingschool.util.ValidationUtil;
import java.io.IOException;
import java.util.*;

public abstract class AbstractFileServlet extends HttpServlet {
    protected abstract String fileName();
    protected abstract String title();
    protected abstract String[] fields();
    protected String[] fieldNames(){ return fields(); }
    protected String idPrefix(){ return "REC"; }
    protected boolean useQueue(){ return false; }
    protected boolean sortExperience(){ return false; }

    protected List<List<String>> records() throws IOException {
        List<List<String>> rows = FileStorage.readRecords(fileName());
        if (useQueue()) rows.sort(Comparator.comparingInt(r -> {
            try { return Integer.parseInt(r.get(r.size()-1)); } catch(Exception e){return 0;}
        }));
        if (sortExperience()) {
            // Bubble sort, descending by the "experience" column (index 4).
            for (int i=0; i<rows.size()-1; i++)
                for (int j=0; j<rows.size()-i-1; j++) {
                    int a = parseInt(rows.get(j),4), b = parseInt(rows.get(j+1),4);
                    if (a < b) Collections.swap(rows,j,j+1);
                }
        }
        return rows;
    }
    private int parseInt(List<String> r,int i){try{return Integer.parseInt(r.get(i));}catch(Exception e){return 0;}}
    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String action=req.getParameter("action"), id=req.getParameter("id"), q=req.getParameter("q");
        List<List<String>> rows=records();
        if ("edit".equals(action) && id!=null) {
            for(List<String> r:rows) if(!r.isEmpty() && r.get(0).equals(id)) req.setAttribute("editRecord",r);
        }
        if(q!=null && !q.isBlank()) {
            String query=q.toLowerCase();
            rows.removeIf(r -> r.stream().noneMatch(v -> v.toLowerCase().contains(query)));
        }
        req.setAttribute("records",rows);
        req.setAttribute("fields",fields());
        req.setAttribute("fieldNames",fieldNames());
        req.setAttribute("pageTitle",title());
        req.setAttribute("moduleUrl",req.getContextPath()+req.getServletPath());
        req.getRequestDispatcher("/WEB-INF/views/module.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String action=req.getParameter("action"), id=req.getParameter("id");
        try {
            if ("create".equals(action) || "update".equals(action)) {
                if ("create".equals(action)) id=idPrefix()+"-"+ValidationUtil.newId();
                List<String> row=new ArrayList<>(); row.add(id);
                for(String f:fields()) row.add(ValidationUtil.required(req.getParameter(f),f));
                if ("create".equals(action)) {
                    if (useQueue()) {
                        row.add(String.valueOf(System.currentTimeMillis()));
                    }
                    if (find(id)!=null) throw new IllegalArgumentException("Duplicate ID");
                    FileStorage.appendRecord(fileName(),row);
                } else {
                    if (!FileStorage.updateById(fileName(),id,row)) throw new IllegalArgumentException("Record not found");
                }
            } else if ("delete".equals(action) || "cancel".equals(action)) {
                if (!FileStorage.deleteById(fileName(),ValidationUtil.required(id,"ID")))
                    throw new IllegalArgumentException("Record not found");
            } else if ("process".equals(action) && useQueue()) {
                List<List<String>> all=FileStorage.readRecords(fileName());
                if(!all.isEmpty()){ all.remove(0); FileStorage.writeRecords(fileName(),all); }
            } else {
                throw new IllegalArgumentException("Unknown action");
            }
            resp.sendRedirect(req.getContextPath()+req.getServletPath());
        } catch(IllegalArgumentException ex) {
            req.setAttribute("error",ex.getMessage());
            req.setAttribute("records",records());
            req.setAttribute("fields",fields());
            req.setAttribute("fieldNames",fieldNames());
            req.setAttribute("pageTitle",title());
            req.setAttribute("moduleUrl",req.getContextPath()+req.getServletPath());
            req.getRequestDispatcher("/WEB-INF/views/module.jsp").forward(req,resp);
        }
    }
    private List<String> find(String id) throws IOException {
        for(List<String> r:FileStorage.readRecords(fileName())) if(!r.isEmpty()&&r.get(0).equals(id))return r;
        return null;
    }
}

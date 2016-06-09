package com.klapa.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.klapa.model.Department;
import com.klapa.model.Employee;
import com.klapa.model.util.HibernateUtil;

/**
 * Servlet implementation class GetParameters
 */
public class GetParameters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetParameters() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void doGet(HttpServletRequest request,
            HttpServletResponse response)    throws ServletException, IOException {
      PrintWriter out = response.getWriter();

      String parametr1 = request.getParameter("parametr1");
      String parametr2 = request.getParameter("parametr2");
      String parametr3 = request.getParameter("parametr3");

      out.println("Wczytanie 3 parametrow z zadania :");
      out.println(parametr1);
      out.println(parametr2);
      out.println(parametr3);
  }
	
	
	public void doPost(HttpServletRequest request,
	        HttpServletResponse response)   throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	    out.println("oracle :");

	    Session session = HibernateUtil.getSessionFactory().openSession();
	    
        session.beginTransaction();
 
        Department department = new Department("java");
        session.save(department);
 
        session.save(new Employee("Jakab Gipsz",department));
        session.save(new Employee("Captain Nemo",department));
      
        session.getTransaction().commit();
 
        Query q = session.createQuery("From Employee ");
                 
        List<Employee> resultList = q.list();
        out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            out.println("next employee: " + next);
        }	    
	    
	    
	    out.println("Wczytanie 3 parametrow z zadania :");
	    out.println(request.getParameter("parametr1"));
	    out.println(request.getParameter("parametr2"));
	    out.println(request.getParameter("parametr3"));
	    
	    float result = 0;
	    int howMany = 0;
	    
	    Enumeration<String> paramNames = request.getParameterNames();
	    List<Float> numbers = new ArrayList<Float>();
	    
	    while( paramNames.hasMoreElements()){
	    	
	    	String nextElement = paramNames.nextElement();
	    	
	        try {  
		    	result += Float.parseFloat(request.getParameter(nextElement));

	        	numbers.add(Float.parseFloat(request.getParameter(nextElement)));   
	         } catch (NumberFormatException e) {  
	        	 out.println("B³êdny parametr: " + request.getParameter(nextElement));
	         } 
	    	howMany ++;
	    }
	    
	    if(howMany > 0)
	    	out.println("Average: " + result/howMany);
	    
	    numbers.sort((a, b) -> a.compareTo(b));
	    
	    for (float number : numbers)
	    	out.println(number);
	    
	}
}

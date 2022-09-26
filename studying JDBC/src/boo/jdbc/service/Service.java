package boo.jdbc.service;

import static boo.jdbc.common.JDBCTemplate.*;
import java.awt.dnd.DropTargetContext;
import java.sql.Connection;

import boo.jdbc.dao.DAO;
import boo.jdbc.vo.VO;

public class Service {

	
	private DAO dao = new DAO();
	
	
	/**영화 상세 조회
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public VO select(String input) throws Exception {
		
		Connection conn = getConnection();
		
		VO vo = dao.select(conn,input);
		
		close(conn);
		
		
		
		return vo;
	}

	

}

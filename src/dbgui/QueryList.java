package dbgui;

public class QueryList {
	private String queryText = "";
	
	public String getQuery(int number)	{
		
		if (number == 1){
			queryText = "SELECT per_first_name, per_last_name, comp_name : " +
					"FROM person NATURAL JOIN works NATURAL JOIN job NATURAL JOIN company : " +
					"ORDER BY comp_name";
			
		}
		else if (number == 2){
			queryText = "SELECT per_first_name, per_last_name, pay_rate: " +
						"FROM person NATURAL JOIN works NATURAL JOIN job NATURAL JOIN company: " +
						"WHERE comp_id = ? AND pay_type = 'salary': " +
						"ORDER BY pay_rate DESC";
			
		}
		else if (number == 3){
			queryText = "SELECT per_first_name, per_last_name, comp_name, : " +
							"CASE WHEN pay_type = 'salary' THEN pay_rate : " +
							"WHEN pay_type = 'wage' AND job_type = 'full-time' THEN pay_rate * 40 * 52 : " +
							"WHEN pay_type = 'wage' AND job_type = 'part-time' THEN pay_rate * 20 * 52 : " +
							"END AS payrate : " +
						"FROM person NATURAL JOIN works NATURAL JOIN job NATURAL JOIN company : " +
						"WHERE comp_id = ? : " +
						"ORDER BY pay_rate DESC";
		}
		else if (number == 4){
			queryText = "SELECT per_id, per_first_name, per_last_name, job_title : " +
						"FROM person NATURAL JOIN works NATURAL JOIN job NATURAL JOIN job_profile : " +
						"WHERE end_date IS NULL";
			
		}
		else if (number == 5){
			queryText = "SELECT per_id, ks_title : " +
						"FROM person NATURAL JOIN obtained_skills NATURAL JOIN knowledge_skill : " +
						"ORDER BY per_id";
			
		}
		
		else if (number == 6){
			queryText = "SELECT per_id, ks_title : " +
						"FROM person NATURAL JOIN works NATURAL JOIN job NATURAL JOIN job_profile : " +
						"NATURAL JOIN required_skill NATURAL JOIN knowledge_skill : " +
						"MINUS  : " +
						"SELECT per_id, ks_title : " +
						"FROM person NATURAL JOIN obtained_skills NATURAL JOIN knowledge_skill";
		}
		
		else if (number == 7){
			queryText = "SELECT pos_code, ks_title, cer_code : " +
						"FROM job_profile NATURAL JOIN required_skill NATURAL JOIN knowledge_skill FULL OUTER JOIN job_cert USING(pos_code) : " +
						"WHERE pos_code = ? ";
			
		}
		else if (number == 8){
			queryText = "WITH skills(ks_title, kind) AS ( : " +
						"SELECT ks_title, 'skill' : " +
						"FROM job NATURAL JOIN job_profile NATURAL JOIN required_skill NATURAL JOIN knowledge_skill WHERE job_code = ? : " +
						"MINUS : " +
						"SELECT ks_title, 'skill' : " +
						"FROM person NATURAL JOIN obtained_skills NATURAL JOIN knowledge_skill : " +
						"WHERE per_id = ? ), : " +
						"certificates(cer_title, kind) AS ( : " +
						"SELECT cer_title, 'certificate' : " +
						"FROM job NATURAL JOIN job_profile NATURAL JOIN job_cert NATURAL JOIN certificate WHERE job_code = ? : " +
						"MINUS : " +
						"SELECT cer_title, 'certificate' : " +
						"FROM person NATURAL JOIN obtained_certificates NATURAL JOIN certificate : " +
						"WHERE per_id = ? ) : " +
						"SELECT ks_title, kind FROM skills UNION SELECT cer_title, kind from certificates";
			
		}
		else if (number == 9) {
			queryText = "SELECT C.c_code FROM course C : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code, cer_code FROM required_skill NATURAL JOIN job_profile FULL OUTER JOIN job_cert USING(pos_code) : " +
						"WHERE pos_code = ? : " +
						"MINUS : " +
						"SELECT ks_code, cer_code FROM course_skill NATURAL JOIN course FULL OUTER JOIN requires USING(c_code) : " +
						"WHERE C.c_code = c_code";
		}
		
		else if (number == 18) {

			queryText = "";
		}
		
		else if (number == 26) {
			queryText = "SELECT sector_name, COUNT(sector_name) as job_count: " +
						"FROM sector NATURAL JOIN company NATURAL JOIN job NATURAL JOIN job_profile: " +
						"GROUP BY sector_name: " +
						"ORDER BY job_count";
		}
		
		else if (number == 50) {
			queryText = "WITH vacant_jobs (job_code) AS ( : " +
						"SELECT job_code FROM job MINUS SELECT job_code FROM works ) : " +
						"SELECT sector_name, COUNT(sector_name) as job_count: " +
						"FROM sector NATURAL JOIN company NATURAL JOIN job NATURAL JOIN job_profile NATURAL JOIN vacant_jobs : " +
						"GROUP BY sector_name: " +
						"ORDER BY job_count";
		}
		
		

		
		return queryText;
		
	}

}

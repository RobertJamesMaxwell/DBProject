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
						"WHERE end_date IS NULL AND per_id = ? : " +
						"MINUS  : " +
						"SELECT per_id, ks_title : " +
						"FROM person NATURAL JOIN obtained_skills NATURAL JOIN knowledge_skill : " +
						"WHERE per_id = ? ";
		}
		
		else if (number == 7){
			queryText = "SELECT DISTINCT ks_title : " +
						"FROM job_profile NATURAL JOIN required_skill NATURAL JOIN knowledge_skill : " +
						"WHERE pos_code = ? ";
			
		}
		else if (number == 8){
			queryText = "WITH skills(ks_title, kind) AS ( : " +
						"SELECT ks_title, 'skill' : " +
						"FROM job NATURAL JOIN required_skill NATURAL JOIN knowledge_skill WHERE job_code = ? : " +
						"MINUS : " +
						"SELECT ks_title, 'skill' : " +
						"FROM obtained_skills NATURAL JOIN knowledge_skill : " +
						"WHERE per_id = ? ), : " +
						"certificates(cer_title, kind) AS ( : " +
						"SELECT cer_title, 'certificate' : " +
						"FROM job NATURAL JOIN job_cert NATURAL JOIN certificate WHERE job_code = ? : " +
						"MINUS : " +
						"SELECT cer_title, 'certificate' : " +
						"FROM obtained_certificates NATURAL JOIN certificate : " +
						"WHERE per_id = ? ) : " +
						"SELECT ks_title, kind FROM skills UNION SELECT cer_title, kind from certificates";
			
		}
		else if (number == 9) {
			queryText = "SELECT C.c_code, 'skill' as covers FROM course C : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM job_profile LEFT OUTER JOIN required_skill USING(pos_code) : " +
						"WHERE pos_code = ?  : " +
						"MINUS : " +
						"SELECT ks_code FROM course_skill LEFT OUTER JOIN course USING(c_code) : " +
						"WHERE C.c_code = c_code and ks_code is not null ) and c_code is not null : " +
						"UNION : " +
						"SELECT C.c_code, 'cert' as covers FROM course C : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM job_profile LEFT OUTER JOIN job_cert USING(pos_code) : " +
						"WHERE pos_code = ?  : " +
						"MINUS : " +
						"SELECT cer_code FROM requires LEFT OUTER JOIN course USING(c_code) : " +
						"WHERE C.c_code = c_code and cer_code is not null ) and c_code is not null";
		}
		
		else if (number == 10) {
			queryText = "WITH missing_skill(ks_code) AS ( : " +
						"SELECT ks_code FROM required_skill NATURAL JOIN job_profile : " +
						"WHERE pos_code = ? : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills NATURAL JOIN person : " +
						"WHERE per_id = ? ),  : " +
						"missing_cert(cer_code) AS ( : " +
						"SELECT cer_code FROM job_cert : " +
						"WHERE pos_code = ? : " +
						"MINUS : " +
						"SELECT cer_code FROM obtained_certificates NATURAL JOIN person : " +
						"WHERE per_id = ? ) : " +
						"SELECT C.c_code, 'cert' as KIND FROM course C : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM missing_cert : " +
						"MINUS : " +
						"SELECT cer_code FROM course LEFT OUTER JOIN requires USING(c_code) : " +
						"WHERE C.c_code = c_code) AND (SELECT COUNT(cer_code) FROM missing_cert) > 0 : " +
						"UNION : " +
						"SELECT C.c_code, 'skill' AS kind FROM course C : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM missing_skill : " +
						"MINUS : " +
						"SELECT ks_code FROM course LEFT OUTER JOIN course_skill USING(c_code) : " +
						"WHERE C.c_code = c_code : " +
						") AND (SELECT COUNT(ks_code) FROM missing_skill) > 0";
		}
		
		else if (number == 11) {
			queryText = "WITH missing_skill(ks_code) AS ( : " +
						"SELECT ks_code FROM required_skill : " +
						"WHERE pos_code = ? : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills : " +
						"WHERE per_id = ? ), : " +
						"missing_cert(cer_code) AS ( : " +
						"SELECT cer_code FROM job_cert : " +
						"WHERE pos_code = ? : " +
						"MINUS : " +
						"SELECT cer_code FROM obtained_certificates : " +
						"WHERE per_id = ? ), : " +
						"needed_course_skill(c_code, sec_no, semester, year, complete_date) AS ( : " +
						"SELECT c.c_code, c.sec_no, c.semester, c.year, c.complete_date FROM section c : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM missing_skill : " +
						"MINUS : " +
						"SELECT ks_code FROM course_skill : " +
						"WHERE C.c_code = c_code ) AND (SELECT COUNT(ks_code) FROM missing_skill) > 0), : " +
						"needed_course_cert(c_code, sec_no, semester, year, complete_date) AS ( : " +
						"SELECT c.c_code, c.sec_no, c.semester, c.year, c.complete_date FROM section c : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM missing_cert : " +
						"MINUS : " +
						"SELECT cer_code FROM requires : " +
						"WHERE C.c_code = c_code ) AND (SELECT COUNT(cer_code) FROM missing_cert) > 0) : " +
						"SELECT c_code, sec_no, semester, year, complete_date, 'cert' AS kind : " +
						"FROM needed_course_cert : " +
						"WHERE complete_date = (SELECT MIN(complete_date) FROM needed_course_cert) : " +
						"UNION : " +
						"SELECT c_code, sec_no, semester, year, complete_date, 'skill' AS kind : " +
						"FROM needed_course_skill : " +
						"WHERE complete_date = (SELECT MIN(complete_date) FROM needed_course_skill) ";
		}
		
		else if (number == 15) {
			queryText = "WITH q_by_cert(pos_code, cer_code) AS ( : " +
						"SELECT J.pos_code, K.cer_code FROM job_profile J LEFT OUTER JOIN job_cert K ON(J.pos_code = K.pos_code) : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM job_cert : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT cer_code FROM obtained_certificates : " +
						"WHERE per_id = ? ) ), : " +
						"q_by_skill(pos_code) AS( : " +
						"SELECT J.pos_code FROM job_profile J : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM required_skill : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills : " +
						"WHERE per_id = ? ) ) : " +
						"SELECT pos_code FROM q_by_skill INTERSECT SELECT pos_code FROM q_by_cert";
		}
		
		else if (number == 18) {
			queryText = "WITH certset(per_id, certcount) AS ( : " +
						"SELECT DISTINCT per_id, COUNT(cer_code) : " +
						"FROM (SELECT cer_code, per_id FROM job_cert NATURAL JOIN obtained_certificates WHERE pos_code = ? ) : " +
						"GROUP BY per_id), : " +
						"skillset(per_id, skillcount) AS ( : " +
						"SELECT DISTINCT per_id, COUNT(ks_code) : " +
						"FROM (SELECT ks_code, per_id FROM required_skill NATURAL JOIN obtained_skills WHERE pos_code = ? ) : " +
						"GROUP BY per_id), : " +
						"qualset(per_id, qualcount) AS (  : " +
						"SELECT per_id, : " +
					  	"(CASE : " +
						"WHEN skillcount IS NULL THEN 0 : " +
						"WHEN skillcount IS NOT NULL THEN skillcount : " +
					  	"END) + : " +
					  	"(CASE : " +
						"WHEN certcount IS NULL THEN 0 : " +
						"WHEN certcount IS NOT NULL THEN certcount : " +
					  	"END ) AS qualcount : " +
						"FROM skillset FULL OUTER JOIN certset USING(per_id) ) : " +
						"SELECT per_id FROM qualset : " +
						"WHERE (((SELECT COUNT(pos_code) FROM job_cert WHERE pos_code = ? ) + (SELECT COUNT(pos_code) FROM required_skill WHERE pos_code = ? )) - qualcount = 1)";			
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

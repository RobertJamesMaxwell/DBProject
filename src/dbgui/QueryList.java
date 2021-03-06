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
		
		else if (number == 16) {
			queryText = "WITH q_by_cert(pos_code, cer_code) AS ( : " +
						"SELECT J.pos_code, K.cer_code  : " +
						"FROM job_profile J FULL OUTER JOIN job_cert K ON(J.pos_code = K.pos_code) : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM job_profile NATURAL JOIN job_cert : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT cer_code FROM person NATURAL JOIN obtained_certificates : " +
						"WHERE per_id = ? ) ), : " +
						"q_by_skill(pos_code) AS( : " +
						"SELECT J.pos_code FROM job_profile J : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM required_skill : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills : " +
						"WHERE per_id = ? ) ), : " +
						"qualified_jobs(pos_code) AS ( : " +
						"SELECT pos_code FROM q_by_skill : " +
						"INTERSECT : " +
						"SELECT pos_code FROM q_by_cert ), : " +
						"pay_of_jobs(job_code, pay_rate) AS ( : " +
						"SELECT job_code, ( : " +
						"CASE WHEN pay_type = 'salary' THEN pay_rate : " +
						"WHEN pay_type = 'wage' AND job_type = 'full-time' THEN pay_rate * 40 * 52 : " +
						"WHEN pay_type = 'wage' AND job_type = 'part-time' THEN pay_rate * 20 * 52 : " +
						"END ) AS pay_rate : " +
						"FROM qualified_jobs NATURAL JOIN job )   : " +
						"SELECT job_code, pay_rate : " +
						"FROM pay_of_jobs : " +
						"WHERE pay_rate = (SELECT MAX(pay_rate) FROM pay_of_jobs)";
		}
		
		else if (number == 17) {
			queryText = "WITH q_by_skill(per_id, per_first_name, per_last_name, per_email) AS ( : " +
						"SELECT P.per_id, per_first_name, per_last_name, per_email FROM person P : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM required_skill : " +
						"WHERE pos_code = ?  : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills : " +
						"WHERE P.per_id = per_id ) ), : " +
						"q_by_cert(per_id, per_first_name, per_last_name, per_email) AS ( : " +
						"SELECT P.per_id, per_first_name, per_last_name, per_email FROM person P : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code FROM job_cert : " +
						"WHERE pos_code = ?  : " +
						"MINUS : " +
						"SELECT cer_code FROM obtained_certificates : " +
						"WHERE P.per_id = per_id ) ) : " +
						"SELECT per_id, per_first_name, per_last_name, per_email : " +
						"FROM q_by_skill : " +
						"INTERSECT : " +
						"SELECT per_id, per_first_name, per_last_name, per_email : " +
						"FROM q_by_cert";
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
		
		else if (number == 19) {
			queryText = "WITH certset(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT per_id, cer_code : " +
						"FROM job_cert NATURAL JOIN obtained_certificates : " +
						"WHERE pos_code = ? ), : " +
						"necessary_cert(cer_code) AS ( : " +
						"SELECT DISTINCT cer_code  : " +
						"FROM job_cert  : " +
						"WHERE pos_code = ? ), : " +
						"missing_certs(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.cer_code : " +
						"FROM necessary_cert N, certset C : " +
						"WHERE EXISTS( : " +
						"SELECT cer_code FROM necessary_cert : " +
						"WHERE cer_code = N.cer_code : " +
						"MINUS : " +
						"SELECT cer_code FROM certset : " +
						"WHERE per_id = C.per_id )  : " +
						"ORDER BY per_id ), : " +
						"missing_cert_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_certs : " +
						"GROUP BY per_id ), : " +
						"skillset(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT per_id, ks_code : " +
						"FROM required_skill NATURAL JOIN obtained_skills : " +
						"WHERE pos_code = ? ), : " +
						"necessary_skill(ks_code) AS ( : " +
						"SELECT DISTINCT ks_code  : " +
						"FROM required_skill  : " +
						"WHERE pos_code = ? ), : " +
						"missing_skills(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.ks_code : " +
						"FROM necessary_skill N, skillset C : " +
						"WHERE EXISTS( : " +
						"SELECT ks_code FROM necessary_skill : " +
						"WHERE ks_code = N.ks_code : " +
						"MINUS : " +
						"SELECT ks_code FROM skillset : " +
						"WHERE per_id = C.per_id)  : " +
						"ORDER BY per_id ), : " +
						"missing_skill_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_skills : " +
						"GROUP BY per_id ) : " +
						"SELECT ks_code, COUNT(ks_code) FROM missing_skills NATURAL JOIN missing_skill_count : " +
						"GROUP BY ks_code : " +
						"HAVING COUNT(ks_code) < 3 ";			
		}
		
		else if (number == 20) {
			queryText = "WITH certset(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT per_id, cer_code : " +
						"FROM job_cert NATURAL JOIN obtained_certificates : " +
						"WHERE pos_code = ? ), : " +
						"necessary_cert(cer_code) AS ( : " +
						"SELECT DISTINCT cer_code  : " +
						"FROM job_cert  : " +
						"WHERE pos_code = ? ), : " +
						"missing_certs(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.cer_code : " +
						"FROM necessary_cert N, certset C : " +
						"WHERE EXISTS( : " +
						"SELECT cer_code FROM necessary_cert : " +
						"WHERE cer_code = N.cer_code : " +
						"MINUS : " +
						"SELECT cer_code FROM certset : " +
						"WHERE per_id = C.per_id)  : " +
						"ORDER BY per_id ), : " +
						"missing_cert_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_certs : " +
						"GROUP BY per_id ), : " +
						"skillset(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT per_id, ks_code : " +
						"FROM required_skill NATURAL JOIN obtained_skills : " +
						"WHERE pos_code = ? ), : " +
						"necessary_skill(ks_code) AS ( : " +
						"SELECT DISTINCT ks_code  : " +
						"FROM required_skill  : " +
						"WHERE pos_code = ? ), : " +
						"missing_skills(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.ks_code : " +
						"FROM necessary_skill N, skillset C : " +
						"WHERE EXISTS( : " +
						"SELECT ks_code FROM necessary_skill : " +
						"WHERE ks_code = N.ks_code : " +
						"MINUS : " +
						"SELECT ks_code FROM skillset : " +
						"WHERE per_id = C.per_id)  : " +
						"ORDER BY per_id ), : " +
						"missing_skill_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_skills : " +
						"GROUP BY per_id ) : " +
						"SELECT per_id, missing_count, 'cert' as kind : " +
						"FROM missing_cert_count : " +
						"WHERE missing_count = (SELECT MIN(missing_count) FROM missing_cert_count) : " +
						"UNION : " +
						"SELECT per_id, missing_count, 'skill' as kind : " +
						"FROM missing_skill_count : " +
						"WHERE missing_count = (SELECT MIN(missing_count) FROM missing_skill_count)";
		}
		
		else if (number == 21) {
			queryText = "WITH PersonRequiredSkillCnt(per_id, skill_count) AS ( : " +
						"SELECT per_id, COUNT(ks_code) : " +
						"FROM obtained_skills NATURAL JOIN required_skill : " +
						"WHERE pos_code = ? : " +
						"GROUP BY per_id ), : " +
						"PersonRequiredCertCnt(per_id, cert_count) AS ( : " +
						"SELECT per_id, COUNT(cer_code) : " +
						"FROM obtained_certificates NATURAL JOIN job_cert : " +
						"WHERE pos_code = ? : " +
						"GROUP BY per_id ) : " +
						"SELECT per_id, (SELECT COUNT(*) FROM required_skill WHERE pos_code = ? ) - skill_count AS num_missing, 'skill' as kind : " +
						"FROM PersonRequiredSkillCnt : " +
						"WHERE skill_count >= (SELECT COUNT(*) - 2 FROM required_skill WHERE pos_code = ? ) : " +
						"UNION : " +
						"SELECT per_id, (SELECT COUNT(*) FROM job_cert WHERE pos_code = ? ) - cert_count AS num_missing, 'cert' as kind : " +
						"FROM PersonRequiredCertCnt : " +
						"WHERE cert_count >= (SELECT COUNT(*) - 2 FROM job_cert WHERE pos_code = ? )";
		}
		
		else if (number == 22) {
			queryText = "WITH certset(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT per_id, cer_code : " +
						"FROM job_cert NATURAL JOIN obtained_certificates : " +
						"WHERE pos_code = ? ), : " +
						"necessary_cert(cer_code) AS ( : " +
						"SELECT DISTINCT cer_code  : " +
						"FROM job_cert  : " +
						"WHERE pos_code = ? ), : " +
						"missing_certs(per_id, cer_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.cer_code : " +
						"FROM necessary_cert N, certset C : " +
						"WHERE EXISTS( : " +
						"SELECT cer_code FROM necessary_cert : " +
						"WHERE cer_code = N.cer_code : " +
						"MINUS : " +
						"SELECT cer_code FROM certset : " +
						"WHERE per_id = C.per_id )  : " +
						"ORDER BY per_id ), : " +
						"missing_cert_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_certs : " +
						"GROUP BY per_id ), : " +
						"skillset(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT per_id, ks_code : " +
						"FROM required_skill NATURAL JOIN obtained_skills : " +
						"WHERE pos_code = ? ), : " +
						"necessary_skill(ks_code) AS ( : " +
						"SELECT DISTINCT ks_code  : " +
						"FROM required_skill  : " +
						"WHERE pos_code = ? ), : " +
						"missing_skills(per_id, ks_code) AS ( : " +
						"SELECT DISTINCT C.per_id, N.ks_code : " +
						"FROM necessary_skill N, skillset C : " +
						"WHERE EXISTS( : " +
						"SELECT ks_code FROM necessary_skill : " +
						"WHERE ks_code = N.ks_code : " +
						"MINUS : " +
						"SELECT ks_code FROM skillset : " +
						"WHERE per_id = C.per_id)  : " +
						"ORDER BY per_id ), : " +
						"missing_skill_count(per_id, missing_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as missing_count : " +
						"FROM missing_skills : " +
						"GROUP BY per_id ) : " +
						"SELECT ks_code, COUNT(ks_code) FROM missing_skills NATURAL JOIN missing_skill_count : " +
						"GROUP BY ks_code : " +
						"HAVING COUNT(ks_code) < 2 ";			
		}
		
		else if (number == 23) {
			queryText = "SELECT DISTINCT per_id, pos_code : " +
						"FROM works NATURAL JOIN job : " +
						"WHERE pos_code = ? ";
		}
		
		else if (number == 24) {
			queryText = "WITH unemployed(per_id) AS ( : " +
						"SELECT per_id FROM person : " +
						"MINUS : " +
						"SELECT per_id FROM works WHERE end_date IS NULL ) : " +
						"SELECT per_id  : " +
						"FROM unemployed NATURAL JOIN works NATURAL JOIN job  : " +
						"WHERE pos_code = ? ";
		}
		
		else if (number == 25) {
			queryText = "WITH emp_count(comp_id, emp_count) AS ( : " +
						"SELECT comp_id, COUNT(comp_id) : " +
						"FROM works NATURAL JOIN job NATURAL JOIN company : " +
						"WHERE end_date IS NULL : " +
						"GROUP BY comp_id ) : " +
						"SELECT comp_id, MAX(emp_count) : " +
						"FROM emp_count : " +
						"WHERE emp_count = (SELECT MAX(emp_count) FROM emp_count) : " +
						"GROUP BY comp_id ";
		}
		
		else if (number == 26) {
			queryText = "SELECT sector_name, COUNT(sector_name) as job_count : " +
						"FROM sector NATURAL JOIN company NATURAL JOIN job NATURAL JOIN job_profile : " +
						"GROUP BY sector_name : " +
						"ORDER BY job_count";
		}
		
		else if (number == 27) {
			queryText = "WITH job_table(per_id, start_date, end_date, job_type, pay_type, pay_rate) AS ( : " +
						"SELECT per_id, start_date, end_date, job_type, pay_type, : " +
						"CASE WHEN pay_type = 'salary' THEN pay_rate : " +
						"WHEN pay_type = 'wage' AND job_type = 'full-time' THEN pay_rate * 40 * 52 : " +
						"WHEN pay_type = 'wage' AND job_type = 'part-time' THEN pay_rate * 20 * 52 : " +
						"END AS payrate : " +
						"FROM job NATURAL JOIN works ), : " +
						"increased (per_id, increased_count) AS ( : " +
						"SELECT per_id, COUNT(per_id) as increased_count  : " +
						"FROM job_table S FULL OUTER JOIN job_table T USING(per_id) : " +
						"WHERE S.pay_rate > T.pay_rate AND S.start_date > T.start_date : " +
						"GROUP BY per_id : " +
						"ORDER BY increased_count ), : " +
						"decreased (per_id, decreased_count) AS( : " +
						"SELECT per_id, COUNT(per_id) as decreased_count  : " +
						"FROM job_table S FULL OUTER JOIN job_table T USING(per_id) : " +
						"WHERE S.pay_rate < T.pay_rate AND S.start_date > T.start_date : " +
						"GROUP BY per_id : " +
						"ORDER BY decreased_count ) : " +
						"SELECT SUM(increased.increased_count) as ratio_dec_to_inc : " +
						"FROM increased : " +
						"UNION : " +
						"SELECT SUM(decreased.decreased_count) : " +
						"FROM decreased ";
		}
		
		else if (number == 28) {
			queryText = "WITH job_openings(job_code, pos_code) AS ( : " +
						"SELECT job_code, pos_code : " +
						"FROM job_profile NATURAL JOIN job LEFT OUTER JOIN works USING (job_code) : " +
						"WHERE per_id IS NULL ), : " +
						"jobless_people(per_id) AS ( : " +
						"SELECT per_id : " +
						"FROM person LEFT OUTER JOIN works USING (per_id) : " +
						"WHERE job_code IS NULL ), : " +
						"openings_cross_people(job_code, pos_code, per_id) AS ( : " +
						"SELECT job_code, pos_code, per_id  : " +
						"FROM job_openings, jobless_people ), : " +
						"q_by_cert(pos_code, cer_code, per_id) AS ( : " +
						"SELECT J.pos_code, K.cer_code, J.per_id  : " +
						"FROM openings_cross_people J FULL OUTER JOIN job_cert K ON(J.pos_code = K.pos_code) : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT cer_code  : " +
						"FROM job_profile NATURAL JOIN job_cert : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT cer_code : " + 
						"FROM openings_cross_people NATURAL JOIN obtained_certificates : " +
						"WHERE J.per_id = per_id ) ), : " +
						"q_by_skill(pos_code, per_id) AS ( : " +
						"SELECT J.pos_code, J.per_id  : " +
						"FROM openings_cross_people J : " +
						"WHERE NOT EXISTS ( : " +
						"SELECT ks_code FROM required_skill : " +
						"WHERE J.pos_code = pos_code : " +
						"MINUS : " +
						"SELECT ks_code FROM obtained_skills : " +
						"WHERE J.per_id = per_id ) ), : " +
						"jobless_q_by_opening (pos_code, per_id) AS ( : " +
						"SELECT pos_code, per_id FROM q_by_skill : " +
						"INTERSECT : " +
						"SELECT pos_code, per_id FROM q_by_cert ), : " +
						"number_openings (pos_code, num_openings) AS ( : " +
						"SELECT pos_code, COUNT(pos_code) AS num_openings : " +
						"FROM job_openings : " +
						"GROUP BY pos_code ), : " +
						"num_q_jobless (pos_code, num_q_jobless) AS ( : " +
						"SELECT pos_code, COUNT(per_id) : " +
						"FROM jobless_q_by_opening : " +
						"GROUP BY pos_code ) : " +
						"SELECT pos_code, num_openings, num_q_jobless, num_openings - num_q_jobless AS difference : " +
						"FROM number_openings LEFT OUTER JOIN num_q_jobless USING(pos_code) : " +
						"WHERE num_q_jobless IS NOT NULL : " +
						"ORDER BY pos_code ";
		}
		
		else if (number == 29) {
			queryText = "WITH jobs_in_sector (job_code, sector_name, pos_code, per_id, start_date, end_date, job_type, pay_type, pay_rate) AS ( : " +
						"SELECT job_code, sector_name, pos_code, per_id, start_date, end_date, job_type, pay_type, : " +
						"CASE WHEN pay_type = 'salary' THEN pay_rate : " +
						"WHEN pay_type = 'wage' AND job_type = 'full-time' THEN pay_rate * 40 * 52 : " +
						"WHEN pay_type = 'wage' AND job_type = 'part-time' THEN pay_rate * 20 * 52 : " +
						"END AS payrate : " +
						"FROM sector NATURAL JOIN company NATURAL JOIN job NATURAL JOIN works : " +
						"WHERE sector_name = 'Technology' ), : " +
						"person_percent_raise (per_id, percent_raise) AS ( : " +
						"SELECT per_id, (S.pay_rate / T.pay_rate * 100) AS percent_raise : " +
						"FROM jobs_in_sector S FULL OUTER JOIN jobs_in_sector T USING(per_id) : " +
						"WHERE S.pay_rate > T.pay_rate AND S.start_date > T.start_date ) : " +
						"SELECT AVG (percent_raise) : " +
						"FROM person_percent_raise ";
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

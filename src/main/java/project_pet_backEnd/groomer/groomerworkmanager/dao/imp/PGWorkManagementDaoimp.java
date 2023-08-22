package project_pet_backEnd.groomer.groomerworkmanager.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project_pet_backEnd.groomer.groomerworkmanager.dao.PGWorkManagementDao;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class PGWorkManagementDaoimp implements PGWorkManagementDao {

//     以 manId 搜尋對應資料


    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override

    public List<Portfolio> getPortfoliosByManagerId(Integer managerId) {
        String sql = "SELECT p.POR_ID, p.PG_ID, p.POR_TITLE, p.POR_TEXT, p.POR_UPLOAD " +
                "FROM PORTFOLIO p " +
                "JOIN GROOMER g ON p.PG_ID = g.PG_ID " +
                "WHERE g.MAN_ID = :managerId";



        Map<String, Object> map = new HashMap<>();
        map.put("managerId", managerId);

        List<Portfolio> portfolioList = namedParameterJdbcTemplate.query(sql, map, (rs, rowNum) -> {
            Portfolio portfolio = new Portfolio();
            portfolio.setPorId(rs.getInt("POR_ID"));
            portfolio.setPgId(rs.getInt("PG_ID"));
            portfolio.setPorTitle(rs.getString("POR_TITLE"));
            portfolio.setPorText(rs.getString("POR_TEXT"));
            portfolio.setPorUpload(rs.getDate("POR_UPLOAD"));
            return portfolio;
        });

        return portfolioList;
    }
}










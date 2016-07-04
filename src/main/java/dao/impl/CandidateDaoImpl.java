package dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import entity.Candidate;

import java.sql.SQLException;

public class CandidateDaoImpl extends BaseDaoImpl<Candidate, String> implements dao.impl.CandidateDao {
    public CandidateDaoImpl(ConnectionSource connectionSource, Class<Candidate> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}

package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import java.util.List;

public interface BotSentenceDomainExtMapper {

    int batchInsert(List<BotSentenceDomain> list);

    int batchUpdateComDomain(String processId);
}
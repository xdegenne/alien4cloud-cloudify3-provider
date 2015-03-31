package alien4cloud.paas.cloudify3.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import alien4cloud.paas.cloudify3.model.EventType;
import alien4cloud.paas.cloudify3.model.Workflow;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Access to events generated by custom workflow execution
 *
 * @author Minh Khang VU
 */
@Component
public class WorkflowResultDAO extends AbstractEventDAO {

    @Override
    protected QueryBuilder createEventsQuery(String executionId, Date timestamp) {
        BoolQueryBuilder eventsQuery = QueryBuilders.boolQuery();
        if (executionId != null && !executionId.isEmpty()) {
            eventsQuery.must(QueryBuilders.matchQuery("context.execution_id", executionId));
        }
        if (timestamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestamp);
            eventsQuery.must(QueryBuilders.rangeQuery("@timestamp").gt(DatatypeConverter.printDateTime(calendar)));
        }
        eventsQuery.must(QueryBuilders.matchQuery("type", "cloudify_event"));
        // Workflow query
        eventsQuery.must(
                QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("event_type", EventType.WORKFLOW_SUCCEEDED))
                        .should(QueryBuilders.matchQuery("event_type", EventType.WORKFLOW_FAILED))).must(
                QueryBuilders.matchQuery("workflow_id", Workflow.EXECUTE_OPERATION));
        return eventsQuery;
    }

    public ListenableFuture<Map<String, String>> getOperationResult() {
        return Futures.immediateFuture(null);
    }
}
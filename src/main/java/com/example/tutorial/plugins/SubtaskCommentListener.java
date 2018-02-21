package com.example.tutorial.plugins;

import com.atlassian.jira.component.ComponentAccessor  ;
import com.atlassian.jira.event.issue.AbstractIssueEventListener;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.comments.CommentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Rock on 2018-02-21.
 */
public class SubtaskCommentListener    extends AbstractIssueEventListener {

    private static final Logger log = LoggerFactory.getLogger(SubtaskCommentListener.class);


    @Override
    public void workflowEvent(IssueEvent event) {
        // only one central way...
        this.customEvent(event);
    }

    @Override
    public  void customEvent(IssueEvent event) {
        // set explicit to debug

        log.debug ("Event: \"${ComponentManager.instance.eventTypeManager.eventTypesMap[event.getEventTypeId()].name}\" fired for ${event.issue}");

        // Here you should put any subtask based restrictions for this task like
        // only for special subtask issue types or similar

        Comment comment = event.getComment();

        if (comment !=null && event.getIssue().isSubTask()) {
            CommentManager commentManager = ComponentAccessor.getCommentManager();
            log.debug ("New commment for subtask found.");
            Issue parent = event.getIssue().getParentObject();
            // Here you should put any parent task based restrictions like
            // only for special issue types or similar

            commentManager.create(parent,
                    comment.getAuthorApplicationUser(),
                    comment.getUpdateAuthorApplicationUser(),
                    comment.getBody(), comment.getGroupLevel(),
                    comment.getRoleLevelId(), comment.getCreated(), comment.getUpdated(), true, true);
            log.debug ("Created comment on ${parent.key}");
        }
    }

}

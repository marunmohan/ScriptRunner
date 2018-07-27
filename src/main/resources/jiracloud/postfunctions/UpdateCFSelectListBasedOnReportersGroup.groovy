package jiracloud.postfunctions

def issueEditMeta = get("/rest/api/2/issue/" + issue.key + "/editmeta").asObject(Map)
def options = issueEditMeta.body.fields.customfield_10036.allowedValues.value
def userGroups = get("/rest/api/2/user/groups?key=" + issue.fields.reporter.key).asObject(List).body.name
def commons = userGroups.intersect(options)
put("/rest/api/2/issue/" + issue.key)
        .queryString("overrideScreenSecurity", Boolean.TRUE)
        .header('Content-Type', 'application/json')
        .body([
        fields: [
                customfield_10036: [value: commons[0]]
        ]
]).asString()
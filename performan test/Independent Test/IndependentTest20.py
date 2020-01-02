from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.addMessage()


    @task(5)
    def addMessage(self):
        params = {
            "addMessageForm": "{\"title\":\"testtitle\",\"content\":\"testcontent\"}",
            "id" : 1
        }
        self.client.get('/addMessage',params = params)


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000

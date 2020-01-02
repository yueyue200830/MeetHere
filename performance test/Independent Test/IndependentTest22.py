from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.editMessage()


    @task(5)
    def editMessage(self):
        params = {
            "editMessageForm":"{\"messageTitle\":\"testtitle\"" +
                             ",\"messageContent\":\"testcontent\"" +
                             ",\"id\":5}"
        }
        self.client.get('/editMessage',params = params)


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000

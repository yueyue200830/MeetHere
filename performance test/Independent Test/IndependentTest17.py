from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getMyMessage()


    @task(5)
    def getMyMessage(self):
        params = {
            "userId": 1,
        }
        self.client.get('/getMyMessage',params = params)


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000

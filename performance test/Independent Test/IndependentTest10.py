from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getAvailable()



    @task(2)
    def getAvailable(self):
        params = {
            "revenueName": "篮球馆",
            "date": "2019-12-31"
        }
        self.client.get('/getAvailable', params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000

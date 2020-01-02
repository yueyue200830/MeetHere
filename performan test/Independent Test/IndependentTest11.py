from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getMyOrder()



    @task(2)
    def getMyOrder(self):
        params = {
            "id": 276,
        }
        self.client.get('/getMyOrder', params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000

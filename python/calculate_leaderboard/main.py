import os
import json

from appwrite.client import Client
from appwrite.services.database import Database

user_collection_id = "617497ffb09f7"
challenges_collection_id = "617872e12c3d8"
user_activities_collection_id = "6174984332af8"
leaderboard_collection_id = "617873ddacaec"


def init_client():
    # Initialize the Appwrite client
    client = Client()
    client.set_endpoint(os.getenv("APPWRITE_ENDPOINT"))
    client.set_project(os.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
    client.set_key(os.getenv("APPWRITE_API_KEY"))

    return client

def main():

    client = init_client()
    database = Database(client)

    # Get all users and calculate their activity scores
    userActivityScores = dict()
    print (f"user collection id: {user_collection_id}")
    res1 = database.list_documents(user_collection_id)
    for userDoc in res1["documents"]:

        userId = userDoc["user_id"]
        # Calculate activity score for the user
        activityScores = dict()

        res2 = database.list_documents(user_activities_collection_id, filters=[f"user_id={userId}"])
        for activityDoc in res2["documents"]:
            activityType = activityDoc["measure_type"][len("HealthDataType."):]
            value = activityDoc["value"]
            if activityType in activityScores:
                activityScores[activityType] += value
            else:
                activityScores[activityType] = value

        userActivityScores[userId] = activityScores
    
    print (userActivityScores)

    # Get all unique challenge ids
    res3 = database.list_documents(challenges_collection_id)
    for challengeDoc in res3["documents"]:
        challengeId = challengeDoc["challenge_id"]
        measureType = challengeDoc["measure_type"][len("MeasureType."):].upper()
        challengeName = challengeDoc["challenge_name"]
        print ("---------------------------------")
        print (f'ChallengeID: {challengeId}')
        print (f'MeasureType: {measureType}')
        print (f'ChallengeName: {challengeName}')
        print ("---------------------------------")

        # Now list of users participating in that challenge
        leaderboardDict = dict()
        res4 = database.list_documents(leaderboard_collection_id, filters=[f"challenge_id={challengeId}"])
        for userDoc in res4["documents"]:
            userId = userDoc["user_id"]
            print (userId)
            if userId in userActivityScores:
                userActivityScoreDict = userActivityScores[userId]
                if measureType in userActivityScoreDict:
                    leaderboardDict[userId] = userActivityScores[userId][measureType]
        
        print ("Leaderboard looks like:")
        print (leaderboardDict)

        sorted_leaderboard = sorted(leaderboardDict.items(), key=lambda x: x[1], reverse=True)
        print ("Leaderboard after sorting looks like:")
        print(sorted_leaderboard)

        # Now update the rank of the users in the challenge
        rank = 0
        for leader in sorted_leaderboard:
            rank += 1
            res5 = database.list_documents(leaderboard_collection_id, filters=[f"user_id={leader[0]}", f"challenge_id={challengeId}"], limit=1)
            docId = res5["documents"][0]["$id"]
            database.update_document(leaderboard_collection_id, docId, {"rank": rank})
    
    print("All done")

if __name__ == "__main__":
    main()

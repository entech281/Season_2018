## Build Status
[![Build Status](https://travis-ci.org/Greenvillians281/Entech281_2018.svg?branch=master)](https://travis-ci.org/Greenvillians281/Entech281_2018)

## FAQ for UNIX lang in bash/terminal

_Instructions imply you're already in the `Team281_2018` directory_

### To push your changes to a branch
1. cd checkout _branchname_
2. git add --all
3. git commit -m '_Enter commit message here_'
4. git push

### To make a merge (pull) request
1. Go to Pull Requests in GitHub
2. Click on New Pull Request
3. Select "base: master" and "compare: _Your Branch Goes Here_"
4. Make a good title and description so reviewers understand your code
5. Add people to review your code

### To deploy to robot
1. Connect to robot's WiFi hotspot 
2. ./gradlew deploy
3. Restart Robot Code in the Driver's Station

## Eclipse help

>Eclipse has lost its mind!
- Dave Cowden

### To format your document
1. Click anywhere in a document or Package Explorer
2. Click Source
3. Click Format or Organize Imports

### To run a test
* ./gradlew build to test for bugfixes
* Right click what you want to be tested in the File Explorer > Run as > JUnit Test

## Helpful commands 
* pwd (to find current directory)
* ls (to see the names of all files inside current directory)
* git reset --hard _CommitIdentifierCode_ (to rollback to a previous commit)
* git reset --hard (to reset to the last _git pull_'d version of a branch)
* git branch _BranchName_ (to create a branch)

##### Last updated 2/6/18 by Santiago #####

# Script to create the repository structure

import os
import sys

rootPath = sys.argv[1]

# Checking root folder for the repository
if not os.path.isdir(rootPath):
    print('The directory is not present. Creating a new one..')
    
    # Creating the repository structure under the root folder
    os.mkdir(rootPath)
    modelsDir = os.path.join(rootPath, 'models')
    os.mkdir(modelsDir)
    implementationsDir = os.path.join(rootPath, 'implementations')
    os.mkdir(implementationsDir)
    examplesDir= os.path.join(rootPath, 'examples')
    os.mkdir(examplesDir)
    dependenciesDir= os.path.join(rootPath, 'dependencies')
    os.mkdir(dependenciesDir)
else:
    print('The directory is present.')
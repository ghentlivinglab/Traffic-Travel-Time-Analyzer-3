#!/bin/bash
command -v svn >/dev/null 2>&1 || { echo "Dit script maakt gebruik van svn (svn (subversion). Gelieve dit te installeren voor gebruik" >&2; exit 1; }  svn export https://github.ugent.be/iii-vop2016/verkeer-3/trunk/deployment . --force --username $1 --password $2;  asadmin redeploy --name verkeersCentrum3 verkeer3gui.war;
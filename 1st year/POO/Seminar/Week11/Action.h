#pragma once

class Action
{
protected:
    Repository& repository;
public:
    Action(Repository& repository) : repository(repository) {}

    virtual void executeUndo() = 0;
    virtual void executeRedo() = 0;

    ~Action() {}
};


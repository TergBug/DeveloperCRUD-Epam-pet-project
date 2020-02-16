package org.mycode.service;

import org.mycode.service.visitors.ServiceVisitor;

public abstract class Service {
    public abstract void doService(ServiceVisitor visitor);
}

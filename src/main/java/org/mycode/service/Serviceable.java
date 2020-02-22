package org.mycode.service;

import org.mycode.service.visitors.ServiceVisitor;

public interface Serviceable {
    void doService(ServiceVisitor visitor);
}

package org.mycode.service.visitors;

import java.util.HashMap;
import java.util.Map;

public final class VisitorFactory {
    public static final int CREATE = 1;
    public static final int GET_BY_ID = 2;
    public static final int GET_ALL = 3;
    public static final int UPDATE = 4;
    public static final int DELETE = 5;
    private static Map<Integer, ServiceVisitor> visitorMap = new HashMap<>();

    private VisitorFactory() {
    }

    public static ServiceVisitor getVisitorByOperation(final int operation, Object inputData) {
        if (!visitorMap.containsKey(operation)) {
            switch (operation) {
                case 1:
                    visitorMap.put(operation, new CreateVisitor(inputData));
                    break;
                case 2:
                    visitorMap.put(operation, new GetByIdVisitor((Long) inputData));
                    break;
                case 3:
                    visitorMap.put(operation, new GetAllVisitor());
                    break;
                case 4:
                    visitorMap.put(operation, new UpdateVisitor(inputData));
                    break;
                case 5:
                    visitorMap.put(operation, new DeleteVisitor((Long) inputData));
                    break;
                default:
                    return null;
            }
        } else {
            visitorMap.get(operation).setInputData(inputData);
        }
        return visitorMap.get(operation);
    }
}
